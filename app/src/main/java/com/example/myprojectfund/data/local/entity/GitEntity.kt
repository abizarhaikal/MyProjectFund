package com.example.myprojectfund.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "git")
data class GitEntity (
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    val username: String,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String

):Parcelable