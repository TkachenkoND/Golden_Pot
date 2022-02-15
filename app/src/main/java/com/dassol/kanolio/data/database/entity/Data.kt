package com.dassol.kanolio.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
data class Data(
    @PrimaryKey
    @ColumnInfo(name = "appsDevKey")
    var appsDevKey: String,

    @ColumnInfo(name = "link")
    var link: String,
)
