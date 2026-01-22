package com.cabme.app.auth

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID

class GoogleAuthHelper(private val context: Context) {

    private val credentialManager = CredentialManager.create(context)

    // Replace with your actual Web Client ID from Google Cloud Console
    companion object {
        private const val WEB_CLIENT_ID = "YOUR_WEB_CLIENT_ID.apps.googleusercontent.com"
        private const val TAG = "GoogleAuthHelper"
    }

    suspend fun signIn(): GoogleSignInResult {
        return withContext(Dispatchers.IO) {
            try {
                val rawNonce = UUID.randomUUID().toString()
                val bytes = rawNonce.toByteArray()
                val md = MessageDigest.getInstance("SHA-256")
                val digest = md.digest(bytes)
                val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(WEB_CLIENT_ID)
                    .setNonce(hashedNonce)
                    .setAutoSelectEnabled(false)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = context
                )

                handleSignInResult(result)
            } catch (e: GetCredentialException) {
                Log.e(TAG, "Google Sign-In failed", e)
                GoogleSignInResult.Error(e.message ?: "Unknown error occurred")
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error during Google Sign-In", e)
                GoogleSignInResult.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    private fun handleSignInResult(result: GetCredentialResponse): GoogleSignInResult {
        return when (val credential = result.credential) {
            is GoogleIdTokenCredential -> {
                val idToken = credential.idToken
                val displayName = credential.displayName
                val email = credential.id

                Log.d(TAG, "Google Sign-In successful: $displayName ($email)")

                GoogleSignInResult.Success(
                    idToken = idToken,
                    displayName = displayName ?: "",
                    email = email
                )
            }
            else -> {
                GoogleSignInResult.Error("Unexpected credential type")
            }
        }
    }
}

sealed class GoogleSignInResult {
    data class Success(
        val idToken: String,
        val displayName: String,
        val email: String
    ) : GoogleSignInResult()

    data class Error(val message: String) : GoogleSignInResult()
}
