package com.dassol.kanolio.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dassol.kanolio.data.database.dao.DataBaseDao
import com.dassol.kanolio.data.database.dao.FullLinkDataBaseDao
import com.dassol.kanolio.data.database.entity.Data
import com.dassol.kanolio.data.database.entity.FullLink

@Database(entities = [Data::class, FullLink::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun getDataBaseDao(): DataBaseDao
    abstract fun getFullLinkDataBaseDao(): FullLinkDataBaseDao
}