package com.cabme.app.navigation

sealed class NavRoutes(val route: String) {
    data object Welcome : NavRoutes("welcome")
    data object Login : NavRoutes("login")
    data object OtpVerification : NavRoutes("otp_verification/{phoneNumber}") {
        fun createRoute(phoneNumber: String) = "otp_verification/$phoneNumber"
    }
    data object PasswordLogin : NavRoutes("password_login/{phoneNumber}") {
        fun createRoute(phoneNumber: String) = "password_login/$phoneNumber"
    }
    data object ForgotPassword : NavRoutes("forgot_password/{email}") {
        fun createRoute(email: String) = "forgot_password/$email"
    }
    data object EmailSent : NavRoutes("email_sent/{email}") {
        fun createRoute(email: String) = "email_sent/$email"
    }
    data object Home : NavRoutes("home")
}
