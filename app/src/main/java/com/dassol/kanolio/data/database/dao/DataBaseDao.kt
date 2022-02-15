package com.dassol.kanolio.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dassol.kanolio.data.database.entity.Data

@Dao
interface DataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppsDevKeyAndLinkInDb(data: Data?)

    @Query("SELECT * from data")
    suspend fun getDataFromDataBase(): Data

    @Query("SELECT * FROM data LIMIT 1")
    suspend fun checkDataBase(): Data?

}