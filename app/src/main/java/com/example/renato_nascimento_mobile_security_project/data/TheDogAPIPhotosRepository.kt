package com.example.renato_nascimento_mobile_security_project.data

import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIPhoto
import com.example.renato_nascimento_mobile_security_project.network.TheDogAPIService

/**
 * Repository that fetch mars photos list from marsApi.
 */
interface TheDogAPIPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi */
    suspend fun getTheDogAPIPhotos(): List<TheDogAPIPhoto>
}

/**
 * Network Implementation of Repository that fetch mars photos list from marsApi.
 */
class NetworkTheDogAPIPhotosRepository(
    private val theDogAPIService: TheDogAPIService
) : TheDogAPIPhotosRepository {
    /** Fetches list of TheDogAPIPhoto from theDogAPI*/
    override suspend fun getTheDogAPIPhotos(): List<TheDogAPIPhoto> = theDogAPIService.getPhotos()
}
