package com.asdec.hauling.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.asdec.hauling.ui.AdminViewModel

@Composable
fun AdminUserScreen(viewModel: AdminViewModel, onBack: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Admin: Create New User", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.newUsername,
            onValueChange = { viewModel.newUsername = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.newPassword,
            onValueChange = { viewModel.newPassword = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = viewModel.selectedRole == "HAULER", onClick = { viewModel.selectedRole = "HAULER" })
            Text("Hauler")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = viewModel.selectedRole == "ADMIN", onClick = { viewModel.selectedRole = "ADMIN" })
            Text("Admin")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.createAccount { onBack() } },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Create User")
        }
    }
}