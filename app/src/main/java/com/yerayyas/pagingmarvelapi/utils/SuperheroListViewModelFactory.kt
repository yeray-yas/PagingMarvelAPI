package com.yerayyas.pagingmarvelapi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yerayyas.pagingmarvelapi.domain.useCases.GetSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.domain.useCases.SearchSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.presentation.superheroes.list.SuperheroListViewModel
import javax.inject.Inject

class SuperheroListViewModelFactory @Inject constructor(
    private val getSuperheroesUseCase: GetSuperheroesUseCase,
    private val searchSuperheroesUseCase: SearchSuperheroesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuperheroListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SuperheroListViewModel(getSuperheroesUseCase, searchSuperheroesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}