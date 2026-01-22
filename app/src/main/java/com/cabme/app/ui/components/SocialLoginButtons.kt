package com.cabme.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.dp
import com.cabme.app.ui.theme.DividerGray

@Composable
fun GoogleIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height

        // Red
        drawArc(
            color = Color(0xFFEA4335),
            startAngle = -45f,
            sweepAngle = -90f,
            useCenter = true,
            size = Size(width, height)
        )

        // Yellow
        drawArc(
            color = Color(0xFFFBBC05),
            startAngle = -135f,
            sweepAngle = -90f,
            useCenter = true,
            size = Size(width, height)
        )

        // Green
        drawArc(
            color = Color(0xFF34A853),
            startAngle = -225f,
            sweepAngle = -90f,
            useCenter = true,
            size = Size(width, height)
        )

        // Blue
        drawArc(
            color = Color(0xFF4285F4),
            startAngle = -315f,
            sweepAngle = -90f,
            useCenter = true,
            size = Size(width, height)
        )

        // White center
        drawCircle(
            color = Color.White,
            radius = width * 0.35f,
            center = Offset(width / 2, height / 2)
        )

        // Blue bar (G shape)
        drawRect(
            color = Color(0xFF4285F4),
            topLeft = Offset(width * 0.5f, height * 0.4f),
            size = Size(width * 0.45f, height * 0.2f)
        )
    }
}

@Composable
fun FacebookIcon(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height

        // Blue background
        drawRect(
            color = Color(0xFF1877F2),
            size = Size(width, height)
        )

        // White F
        val fPath = Path().apply {
            moveTo(width * 0.65f, height * 0.25f)
            lineTo(width * 0.65f, height * 0.4f)
            lineTo(width * 0.55f, height * 0.4f)
            lineTo(width * 0.55f, height * 0.5f)
            lineTo(width * 0.65f, height * 0.5f)
            lineTo(width * 0.65f, height * 0.85f)
            lineTo(width * 0.5f, height * 0.85f)
            lineTo(width * 0.5f, height * 0.5f)
            lineTo(width * 0.4f, height * 0.5f)
            lineTo(width * 0.4f, height * 0.4f)
            lineTo(width * 0.5f, height * 0.4f)
            lineTo(width * 0.5f, height * 0.32f)
            quadraticTo(width * 0.5f, height * 0.15f, width * 0.7f, height * 0.15f)
            lineTo(width * 0.75f, height * 0.15f)
            lineTo(width * 0.75f, height * 0.25f)
            close()
        }
        drawPath(fPath, Color.White, style = Fill)
    }
}

@Composable
fun SocialLoginButtons(
    onGoogleClick: () -> Unit,
    onFacebookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Google button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, DividerGray, CircleShape)
                .clickable { onGoogleClick() },
            contentAlignment = Alignment.Center
        ) {
            GoogleIcon()
        }

        Box(modifier = Modifier.size(32.dp))

        // Facebook button
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.dp, DividerGray, CircleShape)
                .clickable { onFacebookClick() },
            contentAlignment = Alignment.Center
        ) {
            FacebookIcon()
        }
    }
}
