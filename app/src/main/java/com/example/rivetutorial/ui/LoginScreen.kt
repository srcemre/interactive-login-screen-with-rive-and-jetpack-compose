package com.example.rivetutorial.ui

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import app.rive.runtime.kotlin.RiveAnimationView
import com.example.rivetutorial.R


@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = viewModel()
) {
    val uiState by loginViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(paddingMedium()),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(Color(0xFFE3F2FD))
    ) {
        Card(
            modifier = Modifier.run {
                fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = paddingLarge(), start = paddingMedium(), end = paddingMedium())
            },
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFD6E2EA))

        ) {
            AnimationSection(uiState)

            EditNumberField(
                loginViewModel = loginViewModel,
                label = R.string.enter_your_word,
                leadingIcon = Icons.Filled.Person,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                value = uiState.username,
                onValueChanged = { newUsername ->
                    loginViewModel.onUsernameChange(newUsername)
                },
                onFocusChange = { hasFocus ->
                    loginViewModel.onFocusUsername(hasFocus)
                },
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
            )

            EditNumberField(
                loginViewModel = loginViewModel,
                label = R.string.enter_your_password,
                leadingIcon = Icons.Filled.Key,
                isPassword = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                value = uiState.password,
                onValueChanged = { newPassword ->
                    loginViewModel.onPasswordChange(newPassword)
                },
                onFocusChange = { hasFocus ->
                    loginViewModel.onFocusPassword(hasFocus)
                },
                passwordVisible = uiState.passwordVisible,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
                    .fillMaxWidth(),
            )

            LoginButton {
                //loginViewModel.triggerSuccess()
            }

        }
    }
}

@Composable
fun AnimationSection(uiState: LoginUiState) {
    Column(
        verticalArrangement = Arrangement.spacedBy(paddingMedium()),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(paddingMedium()),
    ) {
        if (LocalInspectionMode.current) {
            PreviewAnimation()
        } else {
            ComposableRiveAnimationView(
                animation = R.raw.animated_login_character,
                modifier = Modifier.wrapContentHeight()
            ) { view ->
                view.apply {
                    setBooleanState("Login Machine", "isHandsUp", uiState.passwordVisibleState)
                    setBooleanState("Login Machine", "isChecking", uiState.isChecking)
                    setNumberState("Login Machine", "numLook", uiState.numberState.toFloat())
                    if (uiState.trigFail) fireState("Login Machine", "trigFail")
                    if (uiState.trigSuccess) fireState("Login Machine", "trigSuccess")
                }
            }
        }
    }
}

@Composable
fun PreviewAnimation() {
    // When in design inspection mode, dynamic content such as animations might be difficult to visualize.
    // Therefore, a static preview text is shown instead.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Rive Animation Preview")
    }
}

@Composable
fun ComposableRiveAnimationView(
    modifier: Modifier = Modifier,
    @RawRes animation: Int,
    stateMachineName: String? = null,
    onInit: (RiveAnimationView) -> Unit = {}
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            RiveAnimationView(context).also {
                it.setRiveResource(
                    resId = animation,
                    stateMachineName = stateMachineName,
                )
            }
        },
        update = { view -> onInit(view) }
    )
}

@Composable
fun EditNumberField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    leadingIcon: ImageVector,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onFocusChange: (Boolean) -> Unit,
    loginViewModel: LoginViewModel,
) {
    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        leadingIcon = { Icon(leadingIcon, null) },
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions,
        visualTransformation = when {
            !isPassword -> VisualTransformation.None
            !passwordVisible -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        trailingIcon = {
            if (isPassword) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            loginViewModel.updatePasswordVisibilty(passwordVisible)
                        },
                )
            }
        },

        modifier = modifier.onFocusChanged { focusState ->
            val hasFocus = focusState.isFocused
            if (hasFocus != isFocused) {
                isFocused = hasFocus
                onFocusChange(hasFocus)
            }
        }
    )
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB04863)),
        onClick = onClick
    ) {
        Text(text = stringResource(id = R.string.button_text))
    }
}


@Composable
fun paddingMedium() = dimensionResource(R.dimen.padding_medium)

@Composable
fun paddingLarge() = dimensionResource(R.dimen.padding_large)


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        LoginScreen()
    }
}