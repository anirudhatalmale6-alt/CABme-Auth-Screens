package com.cabme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabme.app.ui.components.ClickableLink
import com.cabme.app.ui.components.DiamondArrowButton
import com.cabme.app.ui.components.OtpInputField
import com.cabme.app.ui.theme.LinkOrange
import com.cabme.app.ui.theme.TextGray
import kotlinx.coroutines.delay

@Composable
fun OtpVerificationScreen(
    phoneNumber: String,
    onBackClick: () -> Unit,
    onOtpVerified: () -> Unit,
    onSignInWithPassword: () -> Unit
) {
    var otpValue by remember { mutableStateOf("") }
    var timerSeconds by remember { mutableIntStateOf(30) }
    var canResend by remember { mutableStateOf(false) }

    // Countdown timer
    LaunchedEffect(timerSeconds) {
        if (timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        } else {
            canResend = true
        }
    }

    // Format phone number for display
    val formattedPhone = remember(phoneNumber) {
        if (phoneNumber.length >= 5) {
            "${phoneNumber.substring(0, 5)} ${phoneNumber.substring(5)}"
        } else {
            phoneNumber
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Back button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
            text = "Enter the 4-digit code sent to you",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "at $formattedPhone",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(48.dp))

        // OTP Input
        OtpInputField(
            otpValue = otpValue,
            onOtpChange = { newValue ->
                otpValue = newValue
                if (newValue.length == 4) {
                    // Auto-submit when 4 digits entered
                    onOtpVerified()
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                // Resend timer
                Text(
                    text = if (canResend) "Resend code" else "Resend code in 0:${timerSeconds.toString().padStart(2, '0')}",
                    color = if (canResend) LinkOrange else TextGray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Sign in with password link
                ClickableLink(
                    text = "Sign in with password",
                    onClick = onSignInWithPassword,
                    color = LinkOrange
                )
            }

            DiamondArrowButton(
                onClick = {
                    if (otpValue.length == 4) {
                        onOtpVerified()
                    }
                },
                enabled = otpValue.length == 4
            )
        }
    }
}
