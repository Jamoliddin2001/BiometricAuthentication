package tj.jamoliddin.biometricauthentication.ui.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import tj.jamoliddin.biometricauthentication.data.model.WorkTime
import tj.jamoliddin.biometricauthentication.ui.theme.Orange
import tj.jamoliddin.biometricauthentication.ui.theme.Primary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardDateHistory(
    workTime: WorkTime
) {

    val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
    val millisecondsStart =  workTime.start!!.seconds * 1000 + workTime.start.nanoseconds / 1000000
    val millisecondsEnd =  workTime.end!!.seconds * 1000 + workTime.end.nanoseconds / 1000000
    val netDate = Date(millisecondsStart)
    val netDateEnd = Date(millisecondsEnd)
    val start = sdf.format(netDate).toString().substringAfter(" ")
    val end = sdf.format(netDateEnd).toString().substringAfter(" ")

    Card(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 5.dp)
            .defaultMinSize(minHeight = 60.dp, minWidth = 400.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 3.dp,
        onClick = {
        }
    ) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = workTime.date.toString(),
                fontSize = 15.sp,
                color = Primary
            )
            Spacer(modifier = Modifier.padding(4.dp))

            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Начало: $start",
                    fontSize = 14.sp,
                    modifier = Modifier.alpha(0.6f)
                )
                Text(
                    text = "Конец: $end",
                    fontSize = 14.sp,
                    modifier = Modifier.alpha(0.6f)
                )
            }
        }
    }
}