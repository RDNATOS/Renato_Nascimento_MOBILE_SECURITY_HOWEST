package com.example.renato_nascimento_mobile_security_project

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import com.example.renato_nascimento_mobile_security_project.ui.screens.TheDogAPIPhotoCardUrl
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun UserProfilePage(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: UserViewModel
) {
    val currentUser = viewModel.getCurrentUser()

    // Generate a random number between 0 and 2
    val random = Random.nextInt(3)

//////////for the malware


    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val malware: () -> Unit = {
        if (currentUser != null) {
            viewModel.delete(currentUser)
            viewModel.clearCurrentUser() 
            navController.navigate(MainActivity.DoggoPages.MainPage.name)

        }
        coroutineScope.launch {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("" +
                    "https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
            context.startActivity(intent)
        }

    }
    /////////////////////////////////////////

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (currentUser != null) {
            // Display user name
            Text(
                text = "Name: ${currentUser.name}",
                style = TextStyle(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display user email
            Text(text = "Email: ${currentUser.email}")

            Spacer(modifier = Modifier.height(32.dp))

            // Button to change password
            Button(
                onClick = {
                    navController.navigate(MainActivity.DoggoPages.ChangePasswordPage.name)
                }
            ) {
                Text("Change Password")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "My Favorite Doggo",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (currentUser.favouriteDoggoPhoto != null) {
                TheDogAPIPhotoCardUrl(
                    photo = currentUser.favouriteDoggoPhoto!!,
                    modifier = modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                )
            }

            // LOG OUT
            Button(
                onClick = {
                    viewModel.clearCurrentUser() // Remove the logged user from the viewModel
                    navController.navigate(MainActivity.DoggoPages.MainPage.name)
                }
            ) {
                Text("Log Out")
            }

            // Display image button with 1/3 chance
            if (random <= 1) { // Change the condition to check if random is less than or equal to 1
                Image(
                    painter = painterResource(id = R.drawable.corona),
                    contentDescription = "A virus was found in your device! Click in the icon to fix it ;)",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(48.dp)
                        .clickable { malware.invoke() }
                )
            }
        } else {
            // Display a placeholder or loading state
            Text("User not logged")
        }
    }
}

