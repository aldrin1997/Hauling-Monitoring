package com.asdec.hauling.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asdec.hauling.data.HaulingRepository
import com.asdec.hauling.data.User
import kotlinx.coroutines.launch
import java.util.UUID

class AdminViewModel(private val repository: HaulingRepository) : ViewModel() {
    var newUsername by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var selectedRole by mutableStateOf("HAULER")

    /**
     * Creates a new user account.
     * Generates a unique UUID to satisfy the User data class requirements.
     */
    fun createAccount(onSuccess: () -> Unit) {
        if (newUsername.isBlank() || newPassword.isBlank()) return

        viewModelScope.launch {
            // Updated User constructor to match common data structures:
            // (id, username, password, role)
            val user = User(
                id = UUID.randomUUID().toString(),
                username = newUsername,
                password = newPassword,
                role = selectedRole
            )

            try {
                repository.createUser(user)

                // Clear fields after successful creation
                newUsername = ""
                newPassword = ""

                onSuccess()
            } catch (e: Exception) {
                // Log error or handle failure state
                android.util.Log.e("AdminViewModel", "Error creating user", e)
            }
        }
    }
}