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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabme.app.ui.components.ClickableLink
import com.cabme.app.ui.components.DiamondArrowButton
import com.cabme.app.ui.theme.DividerGray
import com.cabme.app.ui.theme.LinkOrange
import com.cabme.app.ui.theme.TextGray

@Composable
fun PasswordLoginScreen(
    onBackClick: () -> Unit,
    onPasswordSubmit: () -> Unit,
    onForgotPassword: () -> Unit,
    onDontHaveAccount: () -> Unit
) {
    var password by remember { mutableStateOf("") }

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
            text = "Welcome back, sign in",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Text(
            text = "to continue",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Password input
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            textStyle = TextStyle(
                fontSize = 18.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Box {
                    if (password.isEmpty()) {
                        Text(
                            text = "Enter your password",
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

        // Bottom section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                ClickableLink(
                    text = "I forgot my password",
                    onClick = onForgotPassword,
                    color = LinkOrange
                )

                Spacer(modifier = Modifier.height(8.dp))

                ClickableLink(
                    text = "I don't have an account",
                    onClick = onDontHaveAccount,
                    color = LinkOrange
                )
            }

            DiamondArrowButton(
                onClick = {
                    if (password.isNotEmpty()) {
                        onPasswordSubmit()
                    }
                },
                enabled = password.length >= 6
            )
        }
    }
}
