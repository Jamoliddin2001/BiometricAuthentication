package tj.jamoliddin.biometricauthentication.ui.screens.auth.registration

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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tj.jamoliddin.biometricauthentication.data.model.User
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.domain.util.EMAIL
import tj.jamoliddin.biometricauthentication.domain.util.PASSWORD
import tj.jamoliddin.biometricauthentication.domain.util.PHONE
import tj.jamoliddin.biometricauthentication.domain.util.USER
import tj.jamoliddin.biometricauthentication.ui.components.CustomTextField
import tj.jamoliddin.biometricauthentication.ui.components.Progressbar
import tj.jamoliddin.biometricauthentication.ui.screens.auth.AuthViewModel
import tj.jamoliddin.biometricauthentication.ui.theme.Orange

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistrationScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirm_password by remember {
        mutableStateOf("")
    }
    var first_name by remember {
        mutableStateOf("")
    }
    var second_name by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val state = authViewModel.state.value

    Column(
        modifier = Modifier
            .offset(y = ((-48).dp))
            .padding(horizontal = 64.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Регистрация",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.padding(16.dp))

        CustomTextField(
            onTextChanged = { first_name = it },
            placeHolderText = "Фамилия",
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            ),
            contentDescription = USER
        )

        Spacer(modifier = Modifier.padding(8.dp))

        CustomTextField(
            onTextChanged = { second_name = it },
            placeHolderText = "Имя",
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Sentences
            ),
            contentDescription = USER
        )

        Spacer(modifier = Modifier.padding(8.dp))

        CustomTextField(
            onTextChanged = { phone = it },
            placeHolderText = "Телефон",
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Phone
            ),
            contentDescription = PHONE
        )

        Spacer(modifier = Modifier.padding(8.dp))

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

        Spacer(modifier = Modifier.padding(8.dp))

        CustomTextField(
            onTextChanged = { confirm_password = it },
            placeHolderText = "Повторите пароль",
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            contentDescription = PASSWORD
        )

        Spacer(modifier = Modifier.padding(16.dp))


        Button(
            onClick = {
                if (email != "" && password != "" && first_name != "" && second_name != "" && phone != "" && confirm_password != "") {
                    if (confirm_password == password) {
                        val user = User(phone, first_name, second_name, email)
                        authViewModel.register(email, password, user)
                    } else Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(
                        context,
                        "Заполните все поля для продолжения",
                        Toast.LENGTH_SHORT
                    ).show()
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
                text = "Регистрация",
                fontWeight = FontWeight.Medium
            )
        }

    }

    if(state is UiState.Loading){
        Progressbar()
    }
    if(state is UiState.Success){
        LaunchedEffect(key1 = true){
            navController.navigate(Screen.MainScreen.route){
                popUpTo(0){
                    inclusive = true
                }
            }
        }
    }

    if(state is UiState.Error){
        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
    }
}














