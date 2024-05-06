package com.example.renato_nascimento_mobile_security_project.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIPhoto

class PhotoViewModel : ViewModel() {
    // mutable state to hold the selected photo
    var selectedPhoto = mutableStateOf<TheDogAPIPhoto?>(null)

    // Function to update the selected photo
    fun selectPhoto(photo: TheDogAPIPhoto) {
        selectedPhoto.value = photo
    }
}