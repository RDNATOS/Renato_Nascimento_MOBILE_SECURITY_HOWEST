package com.example.renato_nascimento_mobile_security_project


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import com.example.renato_nascimento_mobile_security_project.ui.screens.TheDogAPIViewModel

class MainActivity : ComponentActivity() {

    //enumeration in order to get the destination of every page in the app
    enum class DoggoPages() {
        StartPage,
        MainPage,
        Login,
        SignUp,
        ProfilePage,
        ChangePasswordPage
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationApp()
                }
        }
    }
}

@Composable
fun NavigationApp(navController: NavHostController = rememberNavController()) {
    NavigationHost(navController = navController)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier,
                   viewModel: UserViewModel = viewModel(factory = UserViewModel.factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    NavHost(
        navController = navController,
        startDestination = MainActivity.DoggoPages.StartPage.name,
        modifier = modifier
    ) {
        composable(route = MainActivity.DoggoPages.StartPage.name) {
            PressButtonToStart(navController = navController)
        }
        composable(route = MainActivity.DoggoPages.MainPage.name) {
            val theDogAPIViewModel: TheDogAPIViewModel = viewModel(factory = TheDogAPIViewModel.Factory)
            MainPage(navController = navController,viewModel)
        }

        composable(route = MainActivity.DoggoPages.Login.name) {
            val allUsersFromDatabase by viewModel.getUsers().collectAsState(emptyList())
            LoginPage.start(navController = navController, modifier = modifier, viewModel, allUsersFromDatabase)
            //LoginPage(navController = navController, modifier = modifier, viewModel, allUsersFromDatabase)
        }

        composable(route = MainActivity.DoggoPages.ProfilePage.name) {
            UserProfilePage(navController = navController, modifier = modifier, viewModel)

        }

        composable(route = MainActivity.DoggoPages.SignUp.name
        ) {
            val allUsersFromDatabase by viewModel.getUsers().collectAsState(emptyList())
            SignUpPage(navController = navController, modifier = modifier, viewModel,allUsersFromDatabase)
        }

        composable(route = MainActivity.DoggoPages.ChangePasswordPage.name
        ) {
            ChangePasswordPage(navController = navController, viewModel)
        }

    }


}


@Composable
fun PressButtonToStart(navController: NavHostController,
                       modifier: Modifier = Modifier) {

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Welcome to the Doggo Finder App!",
            fontSize = 30.sp,
            lineHeight = 35.sp,
        )
        Text(
            text = "Press 'GO' to start searching for your favorite dog :)",
            fontSize = 30.sp,
        )

        Spacer(modifier = Modifier.height(50.dp))

        GoButton(
            onClick = {
                navController.navigate(MainActivity.DoggoPages.MainPage.name)
            },
            modifier = modifier
        )
    }
}


@Composable
fun GoButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (modifier = Modifier.clickable(onClick = onClick)){
            Image(
                painter = painterResource(R.drawable.go),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }
    }
}


