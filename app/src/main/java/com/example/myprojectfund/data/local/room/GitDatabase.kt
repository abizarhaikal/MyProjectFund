package com.example.myprojectfund.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myprojectfund.data.local.entity.GitEntity

@Database(entities = [GitEntity::class] ,version = 1 ,exportSchema = false)
abstract class GitDatabase : RoomDatabase() {
    abstract fun gitDao(): GitDao

    companion object {
        @Volatile
        private var instance: GitDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): GitDatabase {
            if (instance == null) {
                synchronized(GitDatabase::class.java) {
                    instance = Room.databaseBuilder(context.applicationContext,
                        GitDatabase::class.java, "git_database")
                        .build()
                }

            }
            return instance as GitDatabase
        }
    }

}