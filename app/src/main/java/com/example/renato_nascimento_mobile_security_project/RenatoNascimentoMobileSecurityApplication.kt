package com.example.renato_nascimento_mobile_security_project

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer
import com.example.renato_nascimento_mobile_security_project.data.UserDatabase

class RenatoNascimentoMobileSecurityApplication : Application() {

    lateinit var container: AppContainer
    lateinit var database: UserDatabase
    override fun onCreate() {
        super.onCreate()

        database = UserDatabase.getDatabase(this)

        container = DefaultAppContainer(this)
    }
}