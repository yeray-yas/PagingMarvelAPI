package com.yerayyas.pagingmarvelapi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yerayyas.pagingmarvelapi.data.database.model.SuperheroEntity

@Database(entities = [SuperheroEntity::class], version = 1)
abstract class SuperheroDatabase : RoomDatabase() {
    abstract fun superheroDao(): SuperheroDao
}
