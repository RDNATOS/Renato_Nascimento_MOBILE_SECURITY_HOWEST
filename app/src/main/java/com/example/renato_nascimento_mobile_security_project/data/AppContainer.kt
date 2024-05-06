package com.example.marsphotos.data


import android.content.Context
import com.example.renato_nascimento_mobile_security_project.data.NetworkTheDogAPIPhotosRepository
import com.example.renato_nascimento_mobile_security_project.data.OfflineUsersRepository
import com.example.renato_nascimento_mobile_security_project.data.TheDogAPIPhotosRepository
import com.example.renato_nascimento_mobile_security_project.data.UserDatabase
import com.example.renato_nascimento_mobile_security_project.data.UsersRepository
import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIService
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val theDogAPIPhotosRepository: TheDogAPIPhotosRepository
    val usersRepository: UsersRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(private val context : Context) : AppContainer {
    private val baseUrl = "https://api.thedogapi.com"
    //apk_key=live_O9u8ufaLzW85vTJYSErLXZgtlGAsASVrm1UbOfv7wOm1TsjHuxYc7d0At86tMYYg

    /**
     * Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: TheDogAPIService by lazy {
        retrofit.create(TheDogAPIService::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */
    override val theDogAPIPhotosRepository: TheDogAPIPhotosRepository by lazy {
        NetworkTheDogAPIPhotosRepository(retrofitService)
    }

    override val usersRepository: UsersRepository by lazy {
        OfflineUsersRepository(UserDatabase.getDatabase(context).userDao())
    }


}
