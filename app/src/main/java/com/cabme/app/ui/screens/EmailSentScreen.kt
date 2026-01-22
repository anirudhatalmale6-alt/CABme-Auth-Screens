package com.cabme.app.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabme.app.ui.theme.Primary
import com.cabme.app.ui.theme.TextGray

@Composable
fun EmailIcon(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.size(150.dp)) {
        val width = size.width
        val height = size.height

        // Blue circle background
        drawCircle(
            color = Color(0xFF5C6BC0),
            radius = width * 0.45f,
            center = Offset(width / 2, height * 0.4f)
        )

        // Envelope body (yellow/gold)
        val envelopeTop = height * 0.35f
        val envelopeBottom = height * 0.75f
        val envelopeLeft = width * 0.2f
        val envelopeRight = width * 0.8f

        // Envelope back
        drawRoundRect(
            color = Color(0xFFFFCA28),
            topLeft = Offset(envelopeLeft, envelopeTop),
            size = Size(envelopeRight - envelopeLeft, envelopeBottom - envelopeTop),
            cornerRadius = CornerRadius(8f, 8f)
        )

        // Paper inside envelope
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(envelopeLeft + width * 0.05f, envelopeTop - height * 0.15f),
            size = Size(envelopeRight - envelopeLeft - width * 0.1f, height * 0.35f),
            cornerRadius = CornerRadius(4f, 4f)
        )

        // Lines on paper
        val lineY1 = envelopeTop - height * 0.08f
        val lineY2 = envelopeTop - height * 0.02f
        val lineY3 = envelopeTop + height * 0.04f

        drawLine(
            color = Color(0xFF4CAF50),
            start = Offset(envelopeLeft + width * 0.1f, lineY1),
            end = Offset(envelopeRight - width * 0.15f, lineY1),
            strokeWidth = 6f
        )
        drawLine(
            color = Color(0xFFE0E0E0),
            start = Offset(envelopeLeft + width * 0.1f, lineY2),
            end = Offset(envelopeRight - width * 0.2f, lineY2),
            strokeWidth = 4f
        )
        drawLine(
            color = Color(0xFFE0E0E0),
            start = Offset(envelopeLeft + width * 0.1f, lineY3),
            end = Offset(envelopeRight - width * 0.25f, lineY3),
            strokeWidth = 4f
        )

        // Envelope flap (front)
        val flapPath = Path().apply {
            moveTo(envelopeLeft, envelopeTop + 8f)
            lineTo(width / 2, (envelopeTop + envelopeBottom) / 2)
            lineTo(envelopeRight, envelopeTop + 8f)
            lineTo(envelopeRight, envelopeBottom)
            lineTo(envelopeLeft, envelopeBottom)
            close()
        }
        drawPath(flapPath, Color(0xFFFFC107), style = Fill)

        // Darker flap top
        val flapTopPath = Path().apply {
            moveTo(envelopeLeft, envelopeTop + 8f)
            lineTo(width / 2, (envelopeTop + envelopeBottom) / 2 - 10f)
            lineTo(envelopeRight, envelopeTop + 8f)
            close()
        }
        drawPath(flapTopPath, Color(0xFFFFB300), style = Fill)
    }
}

@Composable
fun EmailSentScreen(
    email: String,
    onResend: () -> Unit,
    onOkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.3f))

        // Email icon
        EmailIcon()

        Spacer(modifier = Modifier.height(32.dp))

        // Title
        Text(
            text = "Email sent",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Description
        Text(
            text = "Check your inbox for an Email\nfrom CABme with a link to reset\nyour password",
            fontSize = 16.sp,
            color = TextGray,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.weight(0.3f))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onResend,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "RESEND",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onOkClick,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "OK",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}
