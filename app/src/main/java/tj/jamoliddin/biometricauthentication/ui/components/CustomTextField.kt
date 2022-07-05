package tj.jamoliddin.biometricauthentication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import retrofit2.Retrofit
import tj.jamoliddin.biometricauthentication.domain.util.EMAIL
import tj.jamoliddin.biometricauthentication.domain.util.PASSWORD
import tj.jamoliddin.biometricauthentication.ui.theme.GrayIndicator
import tj.jamoliddin.biometricauthentication.ui.theme.GrayLabel
import tj.jamoliddin.biometricauthentication.ui.theme.Primary

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit = {},
    focusRequester: FocusRequester? = null,
    isPasswordField: Boolean = false,
    placeHolderText: String = "",
    fontSize: TextUnit = 15.sp,
    maxCount: Int = 99,
    textStyle: TextStyle = LocalTextStyle.current.copy(fontSize = fontSize),
    isError: Boolean = false,
    errorText: String = "",
    contentDescription: String = "",
    keyboardActions: KeyboardActions = KeyboardActions { },
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Phone
    )
) {

    var text by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember {
        mutableStateOf(false)
    }

    var visualTransformation = when {
        isPasswordField && passwordVisibility -> VisualTransformation.None
        isPasswordField && !passwordVisibility -> PasswordVisualTransformation()
        !isPasswordField -> VisualTransformation.None
        else -> VisualTransformation.None
    }

    if (keyboardOptions.keyboardType == KeyboardType.Password && !isPasswordField) visualTransformation =
        PasswordVisualTransformation()

    val image = if (!passwordVisibility) Icons.Filled.VisibilityOff
    else Icons.Filled.Visibility


    TextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        label = {
            Text(
                text = if (isError) errorText
                else placeHolderText,
                fontSize = fontSize,
                textAlign = textStyle.textAlign
            )
        },
        onValueChange = {
            if (it.length <= maxCount) {
                onTextChanged(it)
                text = it
            }
        },
        singleLine = true,
        isError = isError,
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = GrayLabel,
            unfocusedLabelColor = GrayLabel,
            backgroundColor = Color.Transparent,
            unfocusedIndicatorColor = GrayIndicator,
            focusedIndicatorColor = Primary,
            cursorColor = Primary
        ),
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        leadingIcon = {
            val icon = when (contentDescription) {
                EMAIL -> Icons.Filled.Email
                PASSWORD -> Icons.Filled.Security
                else -> null
            }
            if(icon != null)
                Icon(imageVector = icon, contentDescription = contentDescription)
        },
        trailingIcon = {
            if (isPasswordField) {
                Icon(
                    modifier = modifier.clickable {
                        passwordVisibility = !passwordVisibility
                    },
                    imageVector = image,
                    contentDescription = null
                )
            }
        }
    )

}