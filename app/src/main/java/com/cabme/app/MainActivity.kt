package com.cabme.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.cabme.app.auth.FacebookAuthHelper
import com.cabme.app.auth.FacebookSignInResult
import com.cabme.app.auth.GoogleAuthHelper
import com.cabme.app.auth.GoogleSignInResult
import com.cabme.app.navigation.NavGraph
import com.cabme.app.navigation.NavRoutes
import com.cabme.app.ui.theme.CABmeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var googleAuthHelper: GoogleAuthHelper
    private lateinit var facebookAuthHelper: FacebookAuthHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        googleAuthHelper = GoogleAuthHelper(this)
        facebookAuthHelper = FacebookAuthHelper(this)

        setContent {
            CABmeTheme {
                val navController = rememberNavController()
                val scope = rememberCoroutineScope()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    NavGraph(
                        navController = navController,
                        onGoogleSignIn = {
                            scope.launch {
                                when (val result = googleAuthHelper.signIn()) {
                                    is GoogleSignInResult.Success -> {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Welcome ${result.displayName}!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        navController.navigate(NavRoutes.Home.route) {
                                            popUpTo(NavRoutes.Welcome.route) { inclusive = true }
                                        }
                                    }
                                    is GoogleSignInResult.Error -> {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Google Sign-In failed: ${result.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        },
                        onFacebookSignIn = {
                            facebookAuthHelper.signIn(
                                onSuccess = { result ->
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Facebook Sign-In successful!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate(NavRoutes.Home.route) {
                                        popUpTo(NavRoutes.Welcome.route) { inclusive = true }
                                    }
                                },
                                onError = { error ->
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Facebook Sign-In failed: ${error.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                    )
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookAuthHelper.callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}
