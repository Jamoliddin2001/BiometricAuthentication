package tj.jamoliddin.biometricauthentication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import tj.jamoliddin.biometricauthentication.ui.theme.PopularChipTextColor
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@Composable
fun LogoutDialog(
    dialogVisibility: MutableState<Boolean>,
    onLogout: () -> Unit
) {
    AnimatedVisibility(visible = dialogVisibility.value) {
        AlertDialog(
            onDismissRequest = { dialogVisibility.value = false },
            title = {
                Text(
                    text = "Выход из аккаунта"
                )
            },
            text = {
                Text(text = "Вы действительно хотите выйти?")
            },
            confirmButton = {
                TextButton(onClick = onLogout) {
                    Text(text = "Да", color = PopularChipTextColor)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogVisibility.value = false
                }) {
                    Text(text = "Отмена", color = Primary)
                }
            }
        )
    }
}