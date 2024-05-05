package com.example.renato_nascimento_mobile_security_project.ui.screens


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renato_nascimento_mobile_security_project.RenatoNascimentoMobileSecurityApplication
import com.example.renato_nascimento_mobile_security_project.data.TheDogAPIPhotosRepository
import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIPhoto
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface TheDogAPIUiState {
    data class Success(val photos: List<TheDogAPIPhoto>) : TheDogAPIUiState
    object Error : TheDogAPIUiState
    object Loading : TheDogAPIUiState
}

class TheDogAPIViewModel(private val theDogAPIPhotosRepository: TheDogAPIPhotosRepository) : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var theDogAPIUiState: TheDogAPIUiState by mutableStateOf(TheDogAPIUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getTheDogAPIPhotos()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [MarsPhoto] [List] [MutableList].
     */
    fun getTheDogAPIPhotos() {
        viewModelScope.launch {
            theDogAPIUiState = TheDogAPIUiState.Loading
            theDogAPIUiState = try {
               TheDogAPIUiState.Success(theDogAPIPhotosRepository.getTheDogAPIPhotos())
            }
            catch (e: IOException) {
                TheDogAPIUiState.Error
            }
        }
    }

    /**
     * Factory for [TheDogAPIViewModel] that takes [TheDogAPIPhotosRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as RenatoNascimentoMobileSecurityApplication)
                val theDogAPIPhotosRepository = application.container.theDogAPIPhotosRepository
                TheDogAPIViewModel(theDogAPIPhotosRepository = theDogAPIPhotosRepository)
            }
        }
    }
}

