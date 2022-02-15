package com.dassol.kanolio.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fullLink")
data class FullLink(
    @PrimaryKey
    @ColumnInfo(name = "fullLink")
    var fullLink: String,
)
