package com.example.renato_nascimento_mobile_security_project.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renato_nascimento_mobile_security_project.RenatoNascimentoMobileSecurityApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao): ViewModel() {
    // var that stores the logged user
    private var loggedUser: User? = null

    // fnction to set the current user
    fun getUsers(): Flow<List<User>> = userDao.getAllUsers()

    fun getUser(id: Int): Flow<User> = userDao.getUser(id)

    //methods from the dao handled here, in the viewModel
    fun update(user: User) {
        viewModelScope.launch {
            userDao.update(user)
        }
    }
    fun delete(user: User) {
        viewModelScope.launch {
            userDao.delete(user)
        }
    }
    suspend fun insertUser(user : User) {
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    ///// to store the current user/////
    fun setCurrentUser(user: User) {
        loggedUser = user
    }

    // function to retrieve the current user
    fun getCurrentUser(): User? {
        return loggedUser
    }

    // Function to clear the current user (on logout)
    fun clearCurrentUser() {
        loggedUser = null
    }
    ////////////////////////////////////

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as RenatoNascimentoMobileSecurityApplication)
                UserViewModel(application.database.userDao())
            }
        }
    }
}
