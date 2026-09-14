package com.example.labactivity10

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.labactivity10.presentation.chat.ChatScreen
import com.example.labactivity10.presentation.chat.ChatViewModel
import com.example.labactivity10.presentation.chat.ChatViewModelFactory
import com.example.labactivity10.ui.theme.LabActivity10Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LabActivity10Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val chatViewModel: ChatViewModel = viewModel(
                            factory = ChatViewModelFactory()
                        )
                        ChatScreen(viewModel = chatViewModel)
                    }
                }
            }
        }
    }
}
