package com.example.renato_nascimento_mobile_security_project.network
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET

interface TheDogAPIService {
    @GET("v1/images/search?limit=26&has_breeds=0&api_key=live_O9u8ufaLzW85vTJYSErLXZgtlGAsASVrm1UbOfv7wOm1TsjHuxYc7d0At86tMYYg")
    //?limit=20&has_breeds=0&api_key=live_O9u8ufaLzW85vTJYSErLXZgtlGAsASVrm1UbOfv7wOm1TsjHuxYc7d0At86tMYYg
    //?limit=10
    suspend fun getPhotos(): List<TheDogAPIPhoto>
}
