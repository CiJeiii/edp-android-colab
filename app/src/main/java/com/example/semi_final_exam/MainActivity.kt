package com.example.semi_final_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.semi_final_exam.ui.AuthUiState
import com.example.semi_final_exam.ui.AuthViewModel
import com.example.semi_final_exam.ui.LoginScreen
import com.example.semi_final_exam.ui.ProfileScreen
import com.example.semi_final_exam.ui.RegisterScreen
import com.example.semi_final_exam.ui.theme.SemiFinalExamTheme

enum class Screen { LOGIN, REGISTER }

class MainActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SemiFinalExamTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainApp(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainApp(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by rememberSaveable { mutableStateOf(Screen.LOGIN) }
    val state = viewModel.uiState

    when (state) {
        is AuthUiState.LoggedIn -> {
            ProfileScreen(
                user = state.user,
                onLogoutClick = {
                    viewModel.logout()
                    currentScreen = Screen.LOGIN
                },
                modifier = modifier
            )
        }
        else -> {
            when (currentScreen) {
                Screen.LOGIN -> {
                    LoginScreen(
                        state = state,
                        onLoginClick = { email, password ->
                            viewModel.login(email, password)
                        },
                        onNavigateToRegister = {
                            viewModel.clearMessage()
                            currentScreen = Screen.REGISTER
                        },
                        modifier = modifier
                    )
                }
                Screen.REGISTER -> {
                    RegisterScreen(
                        state = state,
                        onRegisterClick = { fullName, email, password, birthdate ->
                            viewModel.register(fullName, email, password, birthdate)
                        },
                        onNavigateToLogin = {
                            viewModel.clearMessage()
                            currentScreen = Screen.LOGIN
                        },
                        modifier = modifier
                    )
                }
            }
        }
    }
}
