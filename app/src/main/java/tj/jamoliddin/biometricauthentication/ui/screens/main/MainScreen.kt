package tj.jamoliddin.biometricauthentication.ui.screens.main

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.History
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.Timestamp
import tj.jamoliddin.biometricauthentication.data.model.WorkTime
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.domain.model.Screen
import tj.jamoliddin.biometricauthentication.domain.util.IP_ADDRESS1
import tj.jamoliddin.biometricauthentication.domain.util.IP_ADDRESS2
import tj.jamoliddin.biometricauthentication.domain.util.IP_ADDRESS3
import tj.jamoliddin.biometricauthentication.domain.util.ip_Address
import tj.jamoliddin.biometricauthentication.ui.components.HistoryDialog
import tj.jamoliddin.biometricauthentication.ui.components.LogoutDialog
import tj.jamoliddin.biometricauthentication.ui.components.Progressbar
import tj.jamoliddin.biometricauthentication.ui.theme.Orange
import tj.jamoliddin.biometricauthentication.ui.theme.Primary
import java.text.SimpleDateFormat
import java.util.*

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

    val myHistoryVisibility = remember {
        mutableStateOf(false)
    }

    val start = remember {
        mutableStateOf<Timestamp?>(null)
    }
    val end = remember {
        mutableStateOf<Timestamp?>(null)
    }
    val isStarted = remember {
        mutableStateOf(false)
    }
    val isEnded = remember {
        mutableStateOf(false)
    }

    val stateAddWorkTime = mainScreenViewModel.stateAddWorkTime.value

    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")

    LogoutDialog(dialogVisibility = dialogVisibility) {
        dialogVisibility.value = false
        mainScreenViewModel.clearAll()
        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(0) {
                inclusive = true
            }
        }
    }

    HistoryDialog(dialogVisibility = myHistoryVisibility)

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
                .size(150.dp)
                .align(CenterHorizontally),
            tint = Primary
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Text(text = user?.first_name + "  " + user?.second_name, fontSize = 18.sp)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Контакты:  ${user?.phone}")
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Почта:  ${user?.email}")
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = {
                val ipAddress = getIpAddr(context)
                if (ipAddress in ip_Address/*IP_ADDRESS1 || ipAddress == IP_ADDRESS2 || ipAddress == IP_ADDRESS3*/) {
                    start.value = Timestamp(Date(System.currentTimeMillis()))
                    isStarted.value = true
                    Toast.makeText(context, "Успешно!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Вы не подключены к Wi-Fi", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = !isStarted.value,
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
                text = "Я пришёл!",
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.padding(8.dp))

        OutlinedButton(
            onClick = {
                if (!isEnded.value) {
                    val ipAddress = getIpAddr(context)
                    if (ipAddress in ip_Address /*IP_ADDRESS1 || ipAddress == IP_ADDRESS2 || ipAddress == IP_ADDRESS3*/) {
                        end.value = Timestamp(Date(System.currentTimeMillis()))
                        isEnded.value = true
                        val milliseconds = start.value!!.seconds * 1000 + start.value!!.nanoseconds / 1000000
                        val netDate = Date(milliseconds)
                        val date = sdf.format(netDate).toString().substringBefore(" ")
                        val workTime = WorkTime(
                            date,
                            start.value,
                            end.value
                        )
                        mainScreenViewModel.addWorkTime(workTime)
                        Toast.makeText(context, "Успешно!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Вы не подключены к Wi-Fi", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            },
            enabled = isStarted.value && !isEnded.value,
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
                text = "Я ушёл!",
                fontWeight = FontWeight.Medium
            )
        }


        if (isStarted.value && start.value != null) {
            val milliseconds = start.value!!.seconds * 1000 + start.value!!.nanoseconds / 1000000
            val netDate = Date(milliseconds)
            val date = sdf.format(netDate).toString()
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Начало работы: $date", fontSize = 18.sp)
        }

        if (isEnded.value && end.value != null) {
            val milliseconds = end.value!!.seconds * 1000 + end.value!!.nanoseconds / 1000000
            val netDate = Date(milliseconds)
            val date = sdf.format(netDate).toString()
            Spacer(modifier = Modifier.padding(10.dp))
            Text(text = "Конец работы: $date", fontSize = 18.sp)
        }


    }

    if(stateAddWorkTime is UiState.Loading){
        Progressbar()
    }

    if (stateAddWorkTime is UiState.Success){
        Toast.makeText(context, "Успешно сохранено!", Toast.LENGTH_SHORT).show()
    }

    if(stateAddWorkTime is UiState.Error){
        Toast.makeText(context, stateAddWorkTime.message, Toast.LENGTH_SHORT).show()
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp),
        contentAlignment = Alignment.TopStart
    ) {
        IconButton(onClick = {
            myHistoryVisibility.value = true
        }) {
            Icon(
                imageVector = Icons.Outlined.History,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(33.dp)
            )
        }
    }

}

fun getIpAddr(
    context: Context
): String {
    val wifiManager =
        context.applicationContext.getSystemService(FragmentActivity.WIFI_SERVICE) as WifiManager
    val wifiInfo = wifiManager.connectionInfo
    val ip = wifiInfo.ipAddress
    return String.format(
        "%d.%d.%d.%d",
        ip and 0xff,
        ip shr 8 and 0xff,
        ip shr 16 and 0xff,
        ip shr 24 and 0xff
    )
}