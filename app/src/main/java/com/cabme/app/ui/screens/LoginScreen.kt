package com.cabme.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
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
import com.cabme.app.ui.components.CABmeLogo
import com.cabme.app.ui.components.ClickableLink
import com.cabme.app.ui.components.DiamondArrowButton
import com.cabme.app.ui.components.SocialLoginButtons
import com.cabme.app.ui.components.SocialLoginDivider
import com.cabme.app.ui.theme.DividerGray
import com.cabme.app.ui.theme.LinkOrange
import com.cabme.app.ui.theme.TextGray

@Composable
fun LoginScreen(
    onPhoneSubmit: (String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onFacebookSignIn: () -> Unit,
    onDontHaveAccount: () -> Unit
) {
    var phoneNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top section with logo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.45f)
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            CABmeLogo()
        }

        // Bottom section with form
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.55f)
                .background(Color.White)
                .padding(24.dp)
        ) {
            Text(
                text = "Welcome Back!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Login to continue using CABme",
                fontSize = 16.sp,
                color = TextGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Phone input
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Australia flag emoji and code
                Text(
                    text = "🇦🇺",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "+61",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = phoneNumber,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            phoneNumber = it
                        }
                    },
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.weight(1f),
                    decorationBox = { innerTextField ->
                        Box {
                            if (phoneNumber.isEmpty()) {
                                Text(
                                    text = "Enter your mobile number",
                                    color = TextGray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp),
                color = DividerGray
            )

            Spacer(modifier = Modifier.weight(1f))

            // Bottom row with link and arrow button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableLink(
                    text = "Don't have account?",
                    onClick = onDontHaveAccount,
                    color = LinkOrange
                )

                DiamondArrowButton(
                    onClick = {
                        if (phoneNumber.isNotEmpty()) {
                            onPhoneSubmit(phoneNumber)
                        }
                    },
                    enabled = phoneNumber.length >= 9
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Social login divider
            SocialLoginDivider()

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Or login with Facebook or Google",
                color = TextGray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Social login buttons
            SocialLoginButtons(
                onGoogleClick = onGoogleSignIn,
                onFacebookClick = onFacebookSignIn
            )
        }
    }
}
