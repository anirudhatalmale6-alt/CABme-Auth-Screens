package com.cabme.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cabme.app.ui.theme.GoldLight
import com.cabme.app.ui.theme.GoldPrimary
import com.cabme.app.ui.theme.GoldSecondary
import com.cabme.app.ui.theme.Primary

@Composable
fun CarIcon(
    modifier: Modifier = Modifier,
    size: Dp = 60.dp,
    color: Color = GoldPrimary
) {
    Canvas(modifier = modifier.size(size)) {
        val width = size.toPx()
        val height = size.toPx() * 0.6f

        val goldGradient = Brush.verticalGradient(
            colors = listOf(GoldLight, GoldPrimary, GoldSecondary)
        )

        // Car body
        val bodyPath = Path().apply {
            moveTo(width * 0.1f, height * 0.6f)
            lineTo(width * 0.2f, height * 0.3f)
            lineTo(width * 0.35f, height * 0.15f)
            lineTo(width * 0.65f, height * 0.15f)
            lineTo(width * 0.8f, height * 0.3f)
            lineTo(width * 0.9f, height * 0.6f)
            close()
        }
        drawPath(bodyPath, goldGradient)

        // Windows
        drawPath(
            path = Path().apply {
                moveTo(width * 0.25f, height * 0.35f)
                lineTo(width * 0.35f, height * 0.2f)
                lineTo(width * 0.48f, height * 0.2f)
                lineTo(width * 0.48f, height * 0.35f)
                close()
            },
            color = Color.Black.copy(alpha = 0.3f)
        )

        drawPath(
            path = Path().apply {
                moveTo(width * 0.52f, height * 0.2f)
                lineTo(width * 0.65f, height * 0.2f)
                lineTo(width * 0.75f, height * 0.35f)
                lineTo(width * 0.52f, height * 0.35f)
                close()
            },
            color = Color.Black.copy(alpha = 0.3f)
        )

        // Bottom line
        drawLine(
            brush = goldGradient,
            start = Offset(width * 0.05f, height * 0.65f),
            end = Offset(width * 0.95f, height * 0.65f),
            strokeWidth = 4f
        )

        // Wheels
        drawCircle(
            color = GoldPrimary,
            radius = width * 0.08f,
            center = Offset(width * 0.25f, height * 0.7f)
        )
        drawCircle(
            color = Color.Black,
            radius = width * 0.05f,
            center = Offset(width * 0.25f, height * 0.7f)
        )

        drawCircle(
            color = GoldPrimary,
            radius = width * 0.08f,
            center = Offset(width * 0.75f, height * 0.7f)
        )
        drawCircle(
            color = Color.Black,
            radius = width * 0.05f,
            center = Offset(width * 0.75f, height * 0.7f)
        )

        // Headlights
        drawOval(
            color = GoldLight,
            topLeft = Offset(width * 0.12f, height * 0.45f),
            size = Size(width * 0.08f, width * 0.04f)
        )
        drawOval(
            color = GoldLight,
            topLeft = Offset(width * 0.8f, height * 0.45f),
            size = Size(width * 0.08f, width * 0.04f)
        )
    }
}

@Composable
fun CABmeLogo(
    modifier: Modifier = Modifier,
    showTagline: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CarIcon(size = 70.dp)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(GoldLight, GoldPrimary, GoldSecondary)
                        ),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )
                ) {
                    append("CAB")
                }
                withStyle(
                    style = SpanStyle(
                        brush = Brush.verticalGradient(
                            colors = listOf(GoldLight, GoldPrimary, GoldSecondary)
                        ),
                        fontWeight = FontWeight.Light,
                        fontSize = 36.sp
                    )
                ) {
                    append("me")
                }
            }
        )

        if (showTagline) {
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(
                    text = "PROUDLY ",
                    color = GoldPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "AUS",
                    color = GoldPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )
                Text(
                    text = "TRALIAN",
                    color = GoldSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}
