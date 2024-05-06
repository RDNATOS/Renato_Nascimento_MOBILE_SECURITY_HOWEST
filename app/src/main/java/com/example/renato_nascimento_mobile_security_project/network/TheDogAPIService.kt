package com.example.renato_nascimento_mobile_security_project.network
import retrofit2.http.GET

interface TheDogAPIService {
    @GET("v1/images/search?limit=26&has_breeds=0&api_key=live_O9u8ufaLzW85vTJYSErLXZgtlGAsASVrm1UbOfv7wOm1TsjHuxYc7d0At86tMYYg")
    //?limit=20&has_breeds=0&api_key=live_O9u8ufaLzW85vTJYSErLXZgtlGAsASVrm1UbOfv7wOm1TsjHuxYc7d0At86tMYYg
    //?limit=10
    suspend fun getPhotos(): List<TheDogAPIPhoto>
}
