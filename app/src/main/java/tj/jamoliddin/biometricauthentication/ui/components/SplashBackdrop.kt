package tj.jamoliddin.biometricauthentication.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tj.jamoliddin.biometricauthentication.R
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@Composable
fun SplashBackdrop(
    alpha: Float,
    @DrawableRes animationImageId: Int = R.drawable.ibt
) {
    Surface(color = Primary) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(220.dp)
                        .alpha(alpha = alpha)
                        .clip(CircleShape),
                    alignment = Alignment.Center,
                    painter = painterResource(id = animationImageId),
                    contentDescription = null
                )
            }
        }
    }
}