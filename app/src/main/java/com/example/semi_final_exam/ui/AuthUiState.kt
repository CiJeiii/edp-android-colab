package com.example.semi_final_exam.ui

import com.example.semi_final_exam.domain.model.User

sealed interface AuthUiState {
    data object Idle : AuthUiState
    data object Loading : AuthUiState
    data class LoggedIn(val user: User) : AuthUiState
    data class AccountCreated(val name: String) : AuthUiState
    data class Error(val message: String) : AuthUiState
}
