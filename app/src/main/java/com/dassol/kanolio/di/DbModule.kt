package com.dassol.kanolio.di

import androidx.room.Room
import com.dassol.kanolio.data.database.DataBase
import org.koin.dsl.module

private const val DB_NAME = "Data.db"

val dbModule = module {

    single {
        Room.databaseBuilder(get(), DataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<DataBase>().getDataBaseDao() }
    factory { get<DataBase>().getFullLinkDataBaseDao() }

}