package com.yerayyas.pagingmarvelapi.presentation.superheroes.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiManager
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import com.yerayyas.pagingmarvelapi.databinding.ActivitySuperheroesListBinding
import com.yerayyas.pagingmarvelapi.domain.useCases.GetSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.domain.useCases.SearchSuperheroesUseCase
import com.yerayyas.pagingmarvelapi.presentation.adapters.SuperheroesAdapter
import com.yerayyas.pagingmarvelapi.presentation.superheroes.detail.SuperheroesDetailActivity
import com.yerayyas.pagingmarvelapi.presentation.superheroes.detail.SuperheroesDetailActivity.Companion.EXTRA_ID
import com.yerayyas.pagingmarvelapi.utils.SuperheroListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class SuperheroesListActivity : AppCompatActivity() {
    private val viewModelFactory: SuperheroListViewModelFactory by lazy {
        SuperheroListViewModelFactory(getSuperheroesUseCase, searchSuperheroesUseCase)
    }

    private val viewModel: SuperheroListViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: ActivitySuperheroesListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperheroesAdapter
    private lateinit var repository: SuperheroesRepository
    private val getSuperheroesUseCase: GetSuperheroesUseCase by lazy {
        GetSuperheroesUseCase(repository)
    }
    private val searchSuperheroesUseCase: SearchSuperheroesUseCase by lazy {
        SearchSuperheroesUseCase(repository)
    }

    // Variable to track whether we are in the searchview
      //private var inSearchMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa el repository y el adapter
        val apiService = MarvelApiManager.retrofit.create(MarvelApiService::class.java)
        repository = SuperheroesRepository(apiService)
        retrofit = MarvelApiManager.retrofit

        Picasso.get().setIndicatorsEnabled(true)
        Picasso.get().isLoggingEnabled = true

        adapter = SuperheroesAdapter{ superheroId ->
            navigateToDetail(superheroId)
        }

        val recyclerView = binding.rvSuperhero
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Access the SearchView from the layout
        val searchView = binding.svSuperhero

        // Set up the OnQueryTextListener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    // Launch a coroutine to call the suspend function
                    lifecycleScope.launch {
                        viewModel.setSearchQuery(query)
                    }
                    searchView.clearFocus()
                }
                //inSearchMode = true
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Optionally, you can handle text changes here
                return true
            }
        })

        // Observe the changes in the list of superheroes using Paging
        lifecycleScope.launch {
            viewModel.superheroes.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun navigateToDetail(id:Int){
        val intent = Intent(this, SuperheroesDetailActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        startActivity(intent)
    }
}