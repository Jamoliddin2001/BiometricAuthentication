package tj.jamoliddin.biometricauthentication.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.domain.util.EMAIL
import tj.jamoliddin.biometricauthentication.ui.components.CustomTextField
import tj.jamoliddin.biometricauthentication.ui.components.DialogForgotPassword
import tj.jamoliddin.biometricauthentication.ui.components.Progressbar
import tj.jamoliddin.biometricauthentication.ui.screens.auth.AuthViewModel
import tj.jamoliddin.biometricauthentication.ui.theme.Orange

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember {
        mutableStateOf("")
    }

    val stateForgotPassword = authViewModel.stateForgotPassword.value

    val dialog = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .offset(y = ((-48).dp))
            .padding(horizontal = 64.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Восстановление пароля",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        CustomTextField(
            onTextChanged = { email = it },
            placeHolderText = "Почта",
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text
            ),
            contentDescription = EMAIL
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = {
                if (email != "" ) {
                    authViewModel.forgotPassword(email)
                } else {
                    Toast.makeText(context, "Заполните поля для продолжения", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(size = 12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = Orange
            )
        ) {
            Text(
                text = "Отправить",
                fontWeight = FontWeight.Medium
            )
        }
    }

    DialogForgotPassword(
        dialogVisibility = dialog
    ) {
        navController.navigate(Screen.LoginScreen.route){
            popUpTo(0){
                inclusive = true
            }
        }
    }

    if(stateForgotPassword is UiState.Loading){
        Progressbar()
    }

    if(stateForgotPassword is UiState.Success){
        dialog.value = true
    }

    if(stateForgotPassword is UiState.Error){
        Toast.makeText(context, stateForgotPassword.message, Toast.LENGTH_SHORT).show()
    }
}