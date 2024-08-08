package com.example.rivetutorial.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var isCheckingJob: Job? = null

    fun onPasswordChange(newPassword: String) {
        _uiState.value = _uiState.value.copy(
            password = newPassword,
            isChecking = true,
            numberState = newPassword.length * 3
        )

        if (isCheckingJob?.isActive == true) {
            isCheckingJob?.cancel()
        }
        isCheckingJob = CoroutineScope(Dispatchers.Main).launch {
            delay(1000) // Adjust the delay time as needed
            _uiState.value = _uiState.value.copy(isChecking = false)
        }
    }

    fun onUsernameChange(newUsername: String) {
        _uiState.value = _uiState.value.copy(
            username = newUsername,
            isChecking = true,
            numberState = newUsername.length * 3
        )

        if (isCheckingJob?.isActive == true) {
            isCheckingJob?.cancel()
        }
        isCheckingJob = CoroutineScope(Dispatchers.Main).launch {
            delay(1000) // Adjust the delay time as needed
            _uiState.value = _uiState.value.copy(isChecking = false)
        }
    }

    fun onFocusUsername(hasFocus: Boolean) {
        _uiState.value = _uiState.value.copy(
            passwordVisibleState = if (hasFocus) false else _uiState.value.passwordVisibleState
        )
    }

    fun onFocusPassword(hasFocus: Boolean) {
        _uiState.value = _uiState.value.copy(
            passwordVisibleState = if (hasFocus) !_uiState.value.passwordVisible else _uiState.value.passwordVisibleState
        )
    }

    fun triggerSuccess() {
        _uiState.value = _uiState.value.copy(trigSuccess = true)
    }

    fun triggerFail() {
        _uiState.value = _uiState.value.copy(trigFail = true)
    }

    fun updatePasswordVisibilty(visibility: Boolean) {
        _uiState.value = _uiState.value.copy(passwordVisible = !visibility)
        _uiState.value = _uiState.value.copy(passwordVisibleState = !_uiState.value.passwordVisible)
    }

    fun resetTriggers() {
        _uiState.value = _uiState.value.copy(
            trigSuccess = false,
            trigFail = false
        )
    }

}