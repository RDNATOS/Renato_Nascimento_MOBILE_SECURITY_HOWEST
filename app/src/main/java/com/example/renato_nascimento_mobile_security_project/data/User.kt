package com.example.renato_nascimento_mobile_security_project.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    var favouriteDoggoPhoto: String?,
    var password: String
)
