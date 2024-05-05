package com.example.renato_nascimento_mobile_security_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.renato_nascimento_mobile_security_project.data.User
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun LoginPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel(factory = UserViewModel.factory),
    usersList: List<User>
) {
    // state variables to store the input from the user and check authentication
    val emailState = remember { mutableStateOf(TextFieldValue()) }
    val passwordState = remember { mutableStateOf(TextFieldValue()) }

    // Boolean states to track authentication errors
    var isEmailNotRegistered by remember { mutableStateOf(false) }
    var isPasswordIncorrect by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Message for unregistered email (conditionally displayed)
        if (isEmailNotRegistered) {
            Text(
                text = "This email address is not registered. Please sign up.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Message for incorrect password (conditionally displayed)
        if (isPasswordIncorrect) {
            Text(
                text = "Incorrect password. Please try again.",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // INPUT FIELDS
        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email*") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password*") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // LOGIN
            Button(
                onClick = {
                    // Reset error states
                    isEmailNotRegistered = false
                    isPasswordIncorrect = false

                    val email = emailState.value.text
                    val password = passwordState.value.text
                    var isUserFound = false
                    for (user in usersList) {
                        if (user.email == email) {
                            isUserFound = true
                            if (user.password == password) {
                                // The user exists and the authentication is true
                                viewModel.setCurrentUser(user)
                                navController.navigate(MainActivity.DoggoPages.MainPage.name)
                                return@Button
                            } else {
                                // The password is incorrect
                                isPasswordIncorrect = true
                            }
                        }
                    }
                    // if the code reaches here, it's because there is no user in the database with the provided email
                    if (!isUserFound) {
                        isEmailNotRegistered = true
                    }
                }
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // SIGN UP
            Button(
                onClick = {
                    // Handle sign-up navigation here
                    navController.navigate(MainActivity.DoggoPages.SignUp.name)
                }
            ) {
                Text("Sign Up")
            }
        }
    }
}




/*
@Composable
fun LoginPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel(factory = UserViewModel.factory),
    usersList: List<User>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Email field
        val email = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Enter Email*") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        val password = remember { mutableStateOf(TextFieldValue()) }
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Enter Password*") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Login button
            Button(
                onClick = {
                    navController.navigate("main")
                }
            ) {
                Text("Login")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Sign Up button
            Button(
                onClick = {
                    // Handle sign-up navigation here
                    navController.navigate(MainActivity.DoggoPages.SignUp.name)
                }
            ) {
                Text("Sign Up")
            }
        }
    }
}

 */
