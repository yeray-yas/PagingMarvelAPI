package com.yerayyas.pagingmarvelapi.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import com.yerayyas.pagingmarvelapi.presentation.superheroes.list.SuperheroListViewModel

class SuperheroListViewModelFactory(private val repository: SuperheroesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuperheroListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SuperheroListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}