package com.yerayyas.pagingmarvelapi.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "superheroes")
data class SuperheroEntity(
    @PrimaryKey val superheroId: Int,
    val name: String,
    val description: String,
    val imageUrl: String
)
