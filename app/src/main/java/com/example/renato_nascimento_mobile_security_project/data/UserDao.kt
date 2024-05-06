package com.example.renato_nascimento_mobile_security_project.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)
    @Update
    suspend fun update(user : User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE id = :id")
    fun getUser(id: Int): Flow<User>

    @Query("SELECT * from users ORDER BY name ASC")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT favouriteDoggoPhoto FROM users WHERE email = :email")
    fun getFavouriteDoggoPhoto(email: String): Flow<String?>

    @Query("UPDATE users SET favouriteDoggoPhoto = :newPhoto WHERE email = :email")
    suspend fun updateFavouriteDoggoPhoto(email: String, newPhoto: String)


}