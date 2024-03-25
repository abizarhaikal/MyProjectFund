package com.example.myprojectfund.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myprojectfund.data.local.entity.GitEntity

@Dao
interface GitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(git : GitEntity)

    @Delete
    fun delete(git : GitEntity)

    @Query("SELECT * from git order by username ASC")
    fun getAll(): LiveData<List<GitEntity>>

    @Query("SELECT * FROM git WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<GitEntity>

}