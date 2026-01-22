package com.cabme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabme.app.ui.components.DiamondArrowButton
import com.cabme.app.ui.theme.DividerGray
import com.cabme.app.ui.theme.TextGray

@Composable
fun ForgotPasswordScreen(
    initialEmail: String = "",
    onBackClick: () -> Unit,
    onEmailSubmit: (String) -> Unit
) {
    var email by remember { mutableStateOf(initialEmail) }

    // Mask email for display (if provided)
    val maskedEmail = remember(initialEmail) {
        if (initialEmail.isNotEmpty() && initialEmail.contains("@")) {
            val parts = initialEmail.split("@")
            val username = parts[0]
            val domain = parts[1]
            val maskedUsername = if (username.length > 2) {
                "${username.first()}${"*".repeat(username.length - 2)}${username.last()}"
            } else {
                username
            }
            "$maskedUsername@$domain"
        } else {
            ""
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
            text = "Enter the email address you used",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "to register",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Email input
        BasicTextField(
            value = email,
            onValueChange = { email = it },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (email.isEmpty() && maskedEmail.isNotEmpty()) {
                        Text(
                            text = maskedEmail,
                            color = TextGray,
                            fontSize = 16.sp
                        )
                    } else if (email.isEmpty()) {
                        Text(
                            text = "Enter your email",
                            color = TextGray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            color = DividerGray
        )

        Spacer(modifier = Modifier.weight(1f))

        // Bottom section with arrow button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DiamondArrowButton(
                onClick = {
                    if (email.isNotEmpty() && email.contains("@")) {
                        onEmailSubmit(email)
                    }
                },
                enabled = email.isNotEmpty() && email.contains("@")
            )
        }
    }
}
