package tj.jamoliddin.biometricauthentication.ui.screens.main

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.ui.components.LogoutDialog
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@Composable
fun MainScreen(
    navController: NavController,
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val user = mainScreenViewModel.user

    val dialogVisibility = remember {
        mutableStateOf(false)
    }

    LogoutDialog(dialogVisibility = dialogVisibility) {
        dialogVisibility.value = false
        mainScreenViewModel.clearAll()
        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .offset(y = ((-48).dp))
            .padding(horizontal = 64.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "person",
            modifier = Modifier
                .size(200.dp)
                .align(CenterHorizontally),
            tint = Primary
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(text = user?.first_name + "  " + user?.second_name, fontSize = 18.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Контакты:  ${user?.phone}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Почта:  ${user?.email}")

    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(onClick = {
            dialogVisibility.value = true
        }) {
            Icon(
                imageVector = Icons.Outlined.ExitToApp,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(33.dp)
            )
        }
    }

}