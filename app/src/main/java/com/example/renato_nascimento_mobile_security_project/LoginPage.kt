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
import androidx.navigation.NavHostController
import com.example.renato_nascimento_mobile_security_project.data.User
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel

class LoginPage {
    companion object {
        @Composable
        @JvmStatic
        fun start(navController: NavHostController, modifier: Modifier, viewModel: UserViewModel, usersList: List<User>) {
            LoginPage().StartLoginPage(navController, modifier, viewModel, usersList)
        }
    }

    fun checkPassword(userPassword : String, password : String) : Boolean {
        return userPassword == password
    }

    @Composable
    fun StartLoginPage(navController: NavHostController, modifier: Modifier, viewModel: UserViewModel, usersList: List<User>) {
        // state variables to store the input from the user and check authentication
        val emailState = remember { mutableStateOf(TextFieldValue()) }
        val passwordState = remember { mutableStateOf(TextFieldValue()) }

        // to track states
        var isEmailNotRegistered by remember { mutableStateOf(false) }
        var isPasswordIncorrect by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isEmailNotRegistered) {
                Text(
                    text = "This email address is not registered. Please sign up.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
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
                        // the error states are reseted
                        isEmailNotRegistered = false
                        isPasswordIncorrect = false

                        val email = emailState.value.text
                        val password = passwordState.value.text
                        var isUserFound = false
                        for (user in usersList) {
                            if (user.email == email) {
                                isUserFound = true
                                if (checkPassword(user.password,password)) {
                                    // the passwords are correct, the user is logged
                                    viewModel.setCurrentUser(user)
                                    navController.navigate(MainActivity.DoggoPages.MainPage.name)
                                    return@Button
                                } else {
                                    //the password is incorrect
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
                        navController.navigate(MainActivity.DoggoPages.SignUp.name)
                    }
                ) {
                    Text("Sign Up")
                }
            }
        }
    }
}
