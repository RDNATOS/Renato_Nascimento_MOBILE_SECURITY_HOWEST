package com.example.renato_nascimento_mobile_security_project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.renato_nascimento_mobile_security_project.MainActivity
import com.example.renato_nascimento_mobile_security_project.R
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIPhoto
import kotlinx.coroutines.launch

@Composable
fun MainPageView(
    navController: NavHostController,
    theDogAPIUiState: TheDogAPIUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: UserViewModel
) {
    val photoViewModel: PhotoViewModel = viewModel()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Buttons section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // if user is not logged, the login button appears
            if (viewModel.getCurrentUser() == null) {
                Button(
                    onClick = {
                        navController.navigate(MainActivity.DoggoPages.Login.name)
                    }
                ) {
                    Text("Login")
                }
            }

            // profile button only appears if the user is logged
            viewModel.getCurrentUser()?.let { user ->
                Button(
                    onClick = {
                        navController.navigate(MainActivity.DoggoPages.ProfilePage.name)
                    }
                ) {
                    Text("Profile")
                }
            }
        }

        // for displaying the photos
        when (theDogAPIUiState) {
            is TheDogAPIUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is TheDogAPIUiState.Success -> PhotosGridScreen(
                navController = navController,
                photos = theDogAPIUiState.photos,
                modifier = modifier,
                photoViewModel = photoViewModel,
                viewModel = viewModel, //passes the viewModel to the GridScreen that displays
                //the doggos
            )
            is TheDogAPIUiState.Error -> ErrorScreen(
                retryAction,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}


@Composable
fun TheDogAPIPhotoCard(photo: TheDogAPIPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo.url)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "Doggo Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TheDogAPIPhotoCardUrl(photo: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photo)
                .crossfade(true).build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = "Doggo Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PhotosGridScreen(
    navController: NavHostController,
    photos: List<TheDogAPIPhoto>,
    modifier: Modifier = Modifier,
    photoViewModel: PhotoViewModel,
    viewModel: UserViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    rememberCoroutineScope()
    val showLoginPopup = remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding
    ) {
        items(photos) { photo ->
            TheDogAPIPhotoCard(
                photo,
                modifier = modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clickable {
                        if (viewModel.getCurrentUser() != null) {


                            viewModel.viewModelScope.launch {

                                val loggedUser = viewModel.getCurrentUser()
                                if (loggedUser != null) {
                                    loggedUser.favouriteDoggoPhoto = photo.url
                                }

                                if (loggedUser != null) {
                                    viewModel.update(loggedUser)
                                }
                            }
                        } else {
                            showLoginPopup.value = true
                        }
                    }
            )
        }
    }

    if (showLoginPopup.value) {
        Snackbar(
            modifier = Modifier.fillMaxWidth(),
            action = {
                Text(
                    text = "okay",
                    modifier = Modifier.clickable {
                        showLoginPopup.value = false
                    }
                )
            }
        ) {
            Text("You need to login to save favorite doggo.")
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "Loading"
    )
}

@Composable
fun ErrorScreen(retryAction: () -> Unit,modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "Failed to Load", modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text("Retry")
        }
    }
}
