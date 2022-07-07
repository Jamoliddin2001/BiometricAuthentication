package tj.jamoliddin.biometricauthentication.ui.screens.auth.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.domain.util.EMAIL
import tj.jamoliddin.biometricauthentication.domain.util.PASSWORD
import tj.jamoliddin.biometricauthentication.ui.components.CustomTextField
import tj.jamoliddin.biometricauthentication.ui.components.Progressbar
import tj.jamoliddin.biometricauthentication.ui.screens.auth.AuthViewModel
import tj.jamoliddin.biometricauthentication.ui.theme.*
import kotlin.math.log

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isAuthorized = authViewModel.isAuthorized

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val loginState = authViewModel.stateLogin.value

    LaunchedEffect(key1 = true){
        if(isAuthorized) navController.navigate(Screen.LoginPasswordScreen.route){
            popUpTo(0){
                inclusive = true
            }
        }
    }

    if(!isAuthorized){
        Column(
            modifier = Modifier
                .offset(y = ((-48).dp))
                .padding(horizontal = 64.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Вход",
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

            Spacer(modifier = Modifier.padding(8.dp))

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


            Button(
                onClick = {
                    if (email != "" && password != "") {
                        authViewModel.login(email, password)
                    } else {
                        Toast.makeText(context, "Заполните поля для входа", Toast.LENGTH_SHORT).show()
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
                    text = "Войти",
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                },
                shape = RoundedCornerShape(size = 12.dp),
                border = BorderStroke(width = 1.dp, color = Orange),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Orange,
                    backgroundColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Text(
                    text = "Зарегистрироваться",
                    fontWeight = FontWeight.Medium
                )
            }

        }
    }

    if (loginState is UiState.Loading) {
        Progressbar()
    }

    if (loginState is UiState.Success) {
        LaunchedEffect(key1 = true) {
            navController.navigate(Screen.MainScreen.route) {
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }

    if (loginState is UiState.Error) {
        LaunchedEffect(key1 = true){
            Toast.makeText(context, loginState.message, Toast.LENGTH_LONG).show()
        }
    }
}













