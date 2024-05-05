package com.example.renato_nascimento_mobile_security_project

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.renato_nascimento_mobile_security_project.data.User
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ChangePasswordPage(
    navController: NavHostController,
    viewModel: UserViewModel
) {
    val newPasswordState = remember { mutableStateOf(TextFieldValue()) }
    val confirmPasswordState = remember { mutableStateOf(TextFieldValue()) }
    val passwordChangeState = remember { mutableStateOf(PasswordChangeState.NONE) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // New password input
        OutlinedTextField(
            value = newPasswordState.value,
            onValueChange = { newPasswordState.value = it },
            label = { Text("New Password*") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm password input
        OutlinedTextField(
            value = confirmPasswordState.value,
            onValueChange = { confirmPasswordState.value = it },
            label = { Text("Confirm new Password*") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

                    // Error message if passwords don't match
                    if (passwordChangeState.value == PasswordChangeState.PASSWORDS_DO_NOT_MATCH) {
                        Text("Passwords do not match", color = Color.Red)
                    }

                    // Text indicating password change success
                    if (passwordChangeState.value == PasswordChangeState.PASSWORD_CHANGED) {
                        Text("Password changed successfully", color = Color.Green)
                    }

        // Button to change password
        Button(
            onClick = {
                Log.d("ChangePasswordPage", "Change password button clicked") // Add this log statement

                val newPassword = newPasswordState.value.text
                val confirmPassword = confirmPasswordState.value.text

                if (newPassword == confirmPassword) {
                    Log.d("ChangePasswordPage", "Passwords match") // Add this log statement

                    viewModel.viewModelScope.launch {
                        val loggedUser = viewModel.getCurrentUser()
                        val updatedUser = loggedUser?.copy(password = newPassword)
                        if (updatedUser != null) {
                            viewModel.update(updatedUser)
                            passwordChangeState.value = PasswordChangeState.PASSWORD_CHANGED
                        }
                    }

                } else {
                    Log.d("ChangePasswordPage", "Passwords don't match") // Add this log statement
                    passwordChangeState.value = PasswordChangeState.PASSWORDS_DO_NOT_MATCH
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Password")
        }

    }
}

enum class PasswordChangeState {
    NONE,
    PASSWORDS_DO_NOT_MATCH,
    PASSWORD_CHANGED
}
