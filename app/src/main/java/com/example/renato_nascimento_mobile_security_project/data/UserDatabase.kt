package com.example.renato_nascimento_mobile_security_project.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//if the the schema of the database table changes,
// the version number needs to be increased
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase()
{
    abstract fun userDao(): UserDao
    companion object {
        @Volatile
        private var Instance: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .build().also { Instance = it }
            }
        }
    }


}

/*

 fun getDatabase(context: Context): UserDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, UserDatabase::class.java, "user_database")
                    .createFromAsset("database/doggoDatabase.db") //retrieves the.db
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
 */