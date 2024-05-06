package com.example.renato_nascimento_mobile_security_project.data

import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIPhoto
import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIService

/**
 * fetch mars photos list from theDogAPI.
 */
interface TheDogAPIPhotosRepository {
    /** Fetches list of TheDogAPIPhoto from theDogAPIi */
    suspend fun getTheDogAPIPhotos(): List<TheDogAPIPhoto>
}

/**
 * Network Implementation of Repository that fetch dog photos list from theDogAPI.
 */
class NetworkTheDogAPIPhotosRepository(
    private val theDogAPIService: TheDogAPIService
) : TheDogAPIPhotosRepository {
    /** Fetches list of TheDogAPIPhoto from theDogAPI*/
    override suspend fun getTheDogAPIPhotos(): List<TheDogAPIPhoto> = theDogAPIService.getPhotos()
}
