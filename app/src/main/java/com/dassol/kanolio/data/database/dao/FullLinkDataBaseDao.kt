package com.dassol.kanolio.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dassol.kanolio.data.database.entity.Data
import com.dassol.kanolio.data.database.entity.FullLink

@Dao
interface FullLinkDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFullLinkInDb(fullLink: FullLink?)

    @Query("SELECT * from fullLink")
    suspend fun getFullLinkFromDataBase(): FullLink

    @Query("SELECT * FROM fullLink LIMIT 1")
    suspend fun checkDataBase(): FullLink?

}