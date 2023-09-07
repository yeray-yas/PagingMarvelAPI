package com.yerayyas.pagingmarvelapi.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yerayyas.pagingmarvelapi.data.database.model.SuperheroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperheroDao {
    @Query("SELECT * FROM superheroes")
    fun getAllSuperheroes(): Flow<List<SuperheroEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(superheroes: List<SuperheroEntity>)
}
