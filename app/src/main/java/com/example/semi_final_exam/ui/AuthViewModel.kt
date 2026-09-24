package com.example.semi_final_exam.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semi_final_exam.data.AppResult
import com.example.semi_final_exam.data.UserRepository
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class AuthViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    var uiState by mutableStateOf<AuthUiState>(AuthUiState.Idle)
        private set

    private val datePattern = Regex("""\d{4}-\d{2}-\d{2}""")

    fun clearMessage() {
        uiState = AuthUiState.Idle
    }

    fun logout() {
        uiState = AuthUiState.Idle
    }

    private fun messageFor(failure: AppResult.Failure): String {
        return when (failure) {
            is AppResult.Failure.WrongLogin -> "Invalid email or password."
            is AppResult.Failure.EmailTaken -> "This email address is already taken."
            is AppResult.Failure.NetworkError -> {
                val isTimeout = failure.throwable is SocketTimeoutException ||
                        failure.throwable?.cause is SocketTimeoutException ||
                        failure.message?.contains("timeout", ignoreCase = true) == true
                if (isTimeout) {
                    "Request timed out. Please try again."
                } else {
                    "No internet connection or network error."
                }
            }
            is AppResult.Failure.UnknownError -> failure.message ?: "An unexpected error occurred."
        }
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            uiState = AuthUiState.Error("Please enter your email and password.")
            return
        }

        uiState = AuthUiState.Loading

        viewModelScope.launch {
            when (val result = repository.login(email, password)) {
                is AppResult.Success -> {
                    uiState = AuthUiState.LoggedIn(result.data)
                }
                is AppResult.Failure -> {
                    uiState = AuthUiState.Error(messageFor(result))
                }
            }
        }
    }

    fun register(fullName: String, email: String, password: String, birthdate: String) {
        if (fullName.isBlank() || email.isBlank() || password.isBlank() || birthdate.isBlank()) {
            uiState = AuthUiState.Error("Please fill in all four fields.")
            return
        }

        if (!email.contains("@")) {
            uiState = AuthUiState.Error("Please enter a valid email.")
            return
        }

        if (password.length < 6) {
            uiState = AuthUiState.Error("Password must be at least 6 characters.")
            return
        }

        if (!datePattern.matches(birthdate.trim())) {
            uiState = AuthUiState.Error("Birthdate must look like 2004-05-17.")
            return
        }

        uiState = AuthUiState.Loading

        viewModelScope.launch {
            when (val result = repository.register(fullName, email, password, birthdate)) {
                is AppResult.Success -> {
                    uiState = AuthUiState.AccountCreated(result.data.fullName)
                }
                is AppResult.Failure -> {
                    uiState = AuthUiState.Error(messageFor(result))
                }
            }
        }
    }
}
