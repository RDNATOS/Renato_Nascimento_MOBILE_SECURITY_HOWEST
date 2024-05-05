package com.example.renato_nascimento_mobile_security_project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.renato_nascimento_mobile_security_project.data.User
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@Composable
fun SignUpPage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel = viewModel(factory = UserViewModel.factory),
    usersList: List<User>
) {
    // inputs from the user
    val name = remember { mutableStateOf(TextFieldValue()) }
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    // check if user was created
    var userCreated by remember { mutableStateOf(false) }

    // check if the user exists already
    var userExists by remember { mutableStateOf(false) }

    // gets the name of the recently created user
    var newUserName by remember { mutableStateOf("") }

    // lambda function to be called when the user clicks on create account
    val onCreateAccountClick: () -> Unit = {
        // coroutine call on viewModelScope.launch
        viewModel.viewModelScope.launch {
            //if there is no user with the same email address, the account is created
            val user = User(
                name = name.value.text,
                email = email.value.text,
                password = password.value.text,
                favouriteDoggoPhoto = null
            )
            val existingUser = usersList.find { it.email == user.email }
            if (existingUser!= null) {
                userExists = true
            } else {
                //the user is created and added to the database
                viewModel.insertUser(user)
                userCreated = true
                newUserName = user.name
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userExists) {
            Text("Account with this email already exists. Try with another one")
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userCreated) {
            Text("User $newUserName created successfully, you can now go back and login")
            Spacer(modifier = Modifier.height(16.dp))
        }

        // INPUT FIELDS
        OutlinedTextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Name*") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email*") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password*") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCreateAccountClick
        ) {
            Text("Create Account")
        }
    }
}
