package com.aliakseilukin.vpntestandroid.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.aliakseilukin.vpntestandroid.R
import com.aliakseilukin.vpntestandroid.domain.stub_vpn.StubVpnStatus

@Composable
fun VpnButton(
    vpnStatus: StubVpnStatus,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()

    val waveScale1 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val waveAlpha1 by infiniteTransition.animateFloat(
        initialValue = 0.22f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val waveScale2 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                delayMillis = 900,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val waveAlpha2 by infiniteTransition.animateFloat(
        initialValue = 0.14f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1800,
                delayMillis = 900,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.18f,
        targetValue = 0.32f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val buttonColor = when (vpnStatus) {
        StubVpnStatus.Disconnected -> MaterialTheme.colorScheme.primary
        StubVpnStatus.Connecting -> MaterialTheme.colorScheme.secondary
        StubVpnStatus.Connected -> MaterialTheme.colorScheme.tertiary
    }

    Box(
        modifier = Modifier.size(260.dp),
        contentAlignment = Alignment.Center
    ) {
        if (vpnStatus == StubVpnStatus.Connected) {
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .graphicsLayer {
                        scaleX = waveScale2
                        scaleY = waveScale2
                        alpha = waveAlpha2
                    }
                    .clip(CircleShape)
                    .background(buttonColor)
            )

            Box(
                modifier = Modifier
                    .size(170.dp)
                    .graphicsLayer {
                        scaleX = waveScale1
                        scaleY = waveScale1
                        alpha = waveAlpha1
                    }
                    .clip(CircleShape)
                    .background(buttonColor)
            )

            Box(
                modifier = Modifier
                    .size(190.dp)
                    .graphicsLayer {
                        alpha = glowAlpha
                    }
                    .clip(CircleShape)
                    .background(buttonColor)
            )
        }

        Button(
            onClick = onClick,
            enabled = vpnStatus != StubVpnStatus.Connecting,
            modifier = Modifier.size(170.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_shield_24),
                    contentDescription = null,
                    modifier = Modifier.size(42.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}