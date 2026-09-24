package com.example.semi_final_exam.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.semi_final_exam.domain.model.User
import java.util.Calendar

fun ageFrom(birthdate: String): Int? {
    return try {
        val parts = birthdate.trim().split("-")
        if (parts.size != 3) return null
        val year = parts[0].toIntOrNull() ?: return null
        val month = parts[1].toIntOrNull() ?: return null
        val day = parts[2].toIntOrNull() ?: return null

        val birthCal = Calendar.getInstance().apply {
            set(year, month - 1, day)
        }
        val today = Calendar.getInstance()

        var age = today[Calendar.YEAR] - birthCal[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < birthCal[Calendar.DAY_OF_YEAR]) {
            age--
        }
        if (age < 0) null else age
    } catch (_: Exception) {
        null
    }
}

@Composable
fun ProfileScreen(
    user: User,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "You successfully logged in!",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2E7D32)
                )
                Text(
                    text = "Welcome back, ${user.fullName}.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1B5E20)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "My Profile",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileRow(label = "Full name", value = user.fullName)
        ProfileRow(label = "Email", value = user.email)
        ProfileRow(label = "Birthdate", value = user.birthdate)

        val age = ageFrom(user.birthdate)
        if (age != null) {
            ProfileRow(label = "Age", value = "$age years old")
        }

        ProfileRow(label = "User ID", value = user.id)

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log out")
        }
    }
}

@Composable
private fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
