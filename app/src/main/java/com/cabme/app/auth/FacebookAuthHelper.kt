package com.cabme.app.auth

import android.app.Activity
import android.util.Log
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

class FacebookAuthHelper(private val activity: Activity) {

    val callbackManager: CallbackManager = CallbackManager.Factory.create()

    companion object {
        private const val TAG = "FacebookAuthHelper"
    }

    init {
        setupCallback()
    }

    private var onSuccess: ((FacebookSignInResult.Success) -> Unit)? = null
    private var onError: ((FacebookSignInResult.Error) -> Unit)? = null

    private fun setupCallback() {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val accessToken = result.accessToken
                    Log.d(TAG, "Facebook Sign-In successful: ${accessToken.userId}")

                    onSuccess?.invoke(
                        FacebookSignInResult.Success(
                            accessToken = accessToken.token,
                            userId = accessToken.userId
                        )
                    )
                }

                override fun onCancel() {
                    Log.d(TAG, "Facebook Sign-In cancelled")
                    onError?.invoke(FacebookSignInResult.Error("Sign-in cancelled"))
                }

                override fun onError(error: FacebookException) {
                    Log.e(TAG, "Facebook Sign-In failed", error)
                    onError?.invoke(FacebookSignInResult.Error(error.message ?: "Unknown error"))
                }
            }
        )
    }

    fun signIn(
        onSuccess: (FacebookSignInResult.Success) -> Unit,
        onError: (FacebookSignInResult.Error) -> Unit
    ) {
        this.onSuccess = onSuccess
        this.onError = onError

        LoginManager.getInstance().logInWithReadPermissions(
            activity,
            callbackManager,
            listOf("email", "public_profile")
        )
    }

    fun signOut() {
        LoginManager.getInstance().logOut()
    }
}

sealed class FacebookSignInResult {
    data class Success(
        val accessToken: String,
        val userId: String
    ) : FacebookSignInResult()

    data class Error(val message: String) : FacebookSignInResult()
}
