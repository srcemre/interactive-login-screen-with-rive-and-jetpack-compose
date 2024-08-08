package com.example.rivetutorial.ui

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val numberState: Int = 0,
    val isChecking: Boolean = false,
    val trigSuccess: Boolean = false,
    var trigFail: Boolean = false,
    val passwordVisible: Boolean = false,
    val passwordVisibleState: Boolean = false
)