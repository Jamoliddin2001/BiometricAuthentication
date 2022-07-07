package tj.jamoliddin.biometricauthentication.ui.screens.auth.login

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import tj.jamoliddin.biometricauthentication.domain.biometric.BiometricPromptUtils
import tj.jamoliddin.biometricauthentication.domain.biometric.BiometricPromptUtils.getActivity
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.domain.util.PASSWORD
import tj.jamoliddin.biometricauthentication.ui.components.CustomTextField
import tj.jamoliddin.biometricauthentication.ui.theme.Orange
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPasswordScreen(
    navController: NavController,
    activity: FragmentActivity
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val canAuthenticateWithBiometric =
        BiometricManager.from(context).canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS


    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .offset(y = ((-48).dp))
            .padding(horizontal = 64.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (canAuthenticateWithBiometric) "Введите пароль или приложите палец"
            else "Введите пароль",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        CustomTextField(
            onTextChanged = { password = it },
            isPasswordField = true,
            placeHolderText = "Пароль",
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            contentDescription = PASSWORD
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Text(
            text = "Забыли пароль?",
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    onClick = {
                        navController.navigate(Screen.ForgotPasswordScreen.route)
                    },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ),
            fontSize = 14.sp,
            color = Orange
        )
        Spacer(modifier = Modifier.padding(16.dp))

        if (canAuthenticateWithBiometric) {
            IconButton(
                onClick = {
                    val biometricPrompt = context.getActivity()?.let {
                        BiometricPromptUtils.createBiometricPrompt(it){
                            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show()
                        }
                    }
                    val promptInfo = BiometricPromptUtils.createPromptInfo(context as Activity)
                    try {
                        biometricPrompt?.authenticate(promptInfo)
                    } catch (e: Exception){
                        Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Fingerprint,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(60.dp)
                )
            }
        }

    }
}