package com.yerayyas.pagingmarvelapi.di

import android.content.Context
import androidx.room.Room
import com.yerayyas.pagingmarvelapi.data.database.SuperheroDao
import com.yerayyas.pagingmarvelapi.data.database.SuperheroDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideSuperheroDatabase(@ApplicationContext context: Context): SuperheroDatabase {
        return Room.databaseBuilder(
            context,
            SuperheroDatabase::class.java,
            "superhero_database"
        ).build()
    }

    @Provides
    fun provideSuperheroDao(superheroDatabase: SuperheroDatabase): SuperheroDao {
        return superheroDatabase.superheroDao()
    }
}
