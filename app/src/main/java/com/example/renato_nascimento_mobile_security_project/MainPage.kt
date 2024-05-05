package com.example.renato_nascimento_mobile_security_project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.renato_nascimento_mobile_security_project.data.UserViewModel
import com.example.renato_nascimento_mobile_security_project.ui.screens.MainPageView
import com.example.renato_nascimento_mobile_security_project.ui.screens.TheDogAPIViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(navController: NavHostController,
             viewModel: UserViewModel) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TheDogAPITopAppBar(scrollBehavior = scrollBehavior) }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val theDogAPIViewModel: TheDogAPIViewModel = viewModel()
            MainPageView(navController = navController,
                theDogAPIUiState = theDogAPIViewModel.theDogAPIUiState,
                retryAction = theDogAPIViewModel::getTheDogAPIPhotos,
                contentPadding = it,
                viewModel = viewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TheDogAPITopAppBar(scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        modifier = modifier
    )
}