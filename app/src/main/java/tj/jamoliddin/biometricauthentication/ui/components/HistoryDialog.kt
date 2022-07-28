package tj.jamoliddin.biometricauthentication.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import tj.jamoliddin.biometricauthentication.domain.UiState
import tj.jamoliddin.biometricauthentication.ui.screens.main.MainScreenViewModel
import tj.jamoliddin.biometricauthentication.ui.theme.PopularChipTextColor
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@Composable
fun HistoryDialog(
    dialogVisibility: MutableState<Boolean>,
    mainScreenViewModel: MainScreenViewModel = viewModel()
) {
    LaunchedEffect(key1 = true) {
        mainScreenViewModel.getMyWorkTime()
    }
    val context = LocalContext.current

    val state = mainScreenViewModel.stateMyWorkTime.value

    AnimatedVisibility(visible = dialogVisibility.value) {
        AlertDialog(
            onDismissRequest = { dialogVisibility.value = false },
            title = {
                Text(
                    modifier = Modifier.padding(top = 20.dp, end = 10.dp),
                    text = "Успеваемость на работу ( последние 7 дни )"
                )
            },
            text = {
                if (state is UiState.Loading) {
                    Progressbar()
                }
                if (state is UiState.Success) {
                    if (state.data.isNotEmpty()) {
                        LazyColumn {
                            items(state.data) { item ->
                                CardDateHistory(workTime = item)
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Пока пусто  : )", fontSize = 14.sp, color = Primary)
                        }
                    }
                }

                if (state is UiState.Error) {
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            },
            confirmButton = {

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