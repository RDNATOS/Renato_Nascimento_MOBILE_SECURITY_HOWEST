package com.example.renato_nascimento_mobile_security_project.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TheDogAPIPhoto(
    @SerialName("breeds")
    val breeds: List<String> = emptyList(),

    @SerialName(value = "id")
    val id: String,
    @SerialName(value = "url")
    val url: String,
    @SerialName(value = "width")
    val width: Int?, //sometimes the dog api returns null data for width that poses a
    // problem while fetching data
    @SerialName(value = "height")
    val height: Int?
)
