package com.yerayyas.pagingmarvelapi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yerayyas.pagingmarvelapi.data.database.SuperheroDao
import com.yerayyas.pagingmarvelapi.domain.useCases.GetSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.domain.useCases.SearchSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.presentation.superheroes.list.SuperheroListViewModel
import javax.inject.Inject

class SuperheroListViewModelFactory @Inject constructor(
    private val getSuperheroesUseCase: GetSuperheroesUseCase,
    private val searchSuperheroesUseCase: SearchSuperheroesUseCase,
    private val superheroDao: SuperheroDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuperheroListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SuperheroListViewModel(getSuperheroesUseCase, searchSuperheroesUseCase, superheroDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}