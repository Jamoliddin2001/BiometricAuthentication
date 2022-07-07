package tj.jamoliddin.biometricauthentication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.dp
import tj.jamoliddin.biometricauthentication.ui.theme.Primary


@Composable
fun DialogForgotPassword(
    dialogVisibility: MutableState<Boolean>,
    onClick: () -> Unit
) {

    AnimatedVisibility(visible = dialogVisibility.value) {
        AlertDialog(
            onDismissRequest = {

            },
            title = {
                Text(
                    text = "Восстановление пароля"
                )
            },
            text = {
                Text(text = "Мы отправили на вашу почту ссылку для восстановления пароля. Перейдите " +
                        "по ссылку и добавьте новый пароль, после чего вы можете заново зайти в систему.'\n' Спасибо :) ")
            },
            confirmButton = {
                TextButton(
                    onClick = onClick
                ) {
                    Text(text = "Понятно", color = Primary)
                }
            },
            shape = RoundedCornerShape(12.dp)
        )
    }

}