package com.cabme.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cabme.app.ui.screens.EmailSentScreen
import com.cabme.app.ui.screens.ForgotPasswordScreen
import com.cabme.app.ui.screens.HomeScreen
import com.cabme.app.ui.screens.LoginScreen
import com.cabme.app.ui.screens.OtpVerificationScreen
import com.cabme.app.ui.screens.PasswordLoginScreen
import com.cabme.app.ui.screens.WelcomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    onGoogleSignIn: () -> Unit,
    onFacebookSignIn: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Welcome.route
    ) {
        composable(NavRoutes.Welcome.route) {
            WelcomeScreen(
                onLoginClick = {
                    navController.navigate(NavRoutes.Login.route)
                }
            )
        }

        composable(NavRoutes.Login.route) {
            LoginScreen(
                onPhoneSubmit = { phoneNumber ->
                    navController.navigate(NavRoutes.OtpVerification.createRoute(phoneNumber))
                },
                onGoogleSignIn = onGoogleSignIn,
                onFacebookSignIn = onFacebookSignIn,
                onDontHaveAccount = {
                    // Navigate to sign up - for now just go back to welcome
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavRoutes.OtpVerification.route,
            arguments = listOf(navArgument("phoneNumber") { type = NavType.StringType })
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            OtpVerificationScreen(
                phoneNumber = phoneNumber,
                onBackClick = { navController.popBackStack() },
                onOtpVerified = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Welcome.route) { inclusive = true }
                    }
                },
                onSignInWithPassword = {
                    navController.navigate(NavRoutes.PasswordLogin.createRoute(phoneNumber))
                }
            )
        }

        composable(
            route = NavRoutes.PasswordLogin.route,
            arguments = listOf(navArgument("phoneNumber") { type = NavType.StringType })
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            PasswordLoginScreen(
                onBackClick = { navController.popBackStack() },
                onPasswordSubmit = {
                    navController.navigate(NavRoutes.Home.route) {
                        popUpTo(NavRoutes.Welcome.route) { inclusive = true }
                    }
                },
                onForgotPassword = {
                    navController.navigate(NavRoutes.ForgotPassword.createRoute(""))
                },
                onDontHaveAccount = {
                    navController.popBackStack(NavRoutes.Welcome.route, false)
                }
            )
        }

        composable(
            route = NavRoutes.ForgotPassword.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ForgotPasswordScreen(
                initialEmail = email,
                onBackClick = { navController.popBackStack() },
                onEmailSubmit = { submittedEmail ->
                    navController.navigate(NavRoutes.EmailSent.createRoute(submittedEmail))
                }
            )
        }

        composable(
            route = NavRoutes.EmailSent.route,
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            EmailSentScreen(
                email = email,
                onResend = { /* Resend email logic */ },
                onOkClick = {
                    navController.popBackStack(NavRoutes.Login.route, false)
                }
            )
        }

        composable(NavRoutes.Home.route) {
            HomeScreen()
        }
    }
}
