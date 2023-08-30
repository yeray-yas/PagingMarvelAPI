package com.yerayyas.pagingmarvelapi.presentation.superheroes.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiManager
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.repository.SuperheroesRepository
import com.yerayyas.pagingmarvelapi.databinding.ActivitySuperheroesListBinding
import com.yerayyas.pagingmarvelapi.presentation.adapters.SuperheroesAdapter
import com.yerayyas.pagingmarvelapi.utils.SuperheroListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class SuperheroesListActivity : AppCompatActivity() {
    private val viewModelFactory: SuperheroListViewModelFactory by lazy {
        SuperheroListViewModelFactory(repository)
    }

    private val viewModel: SuperheroListViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: ActivitySuperheroesListBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: SuperheroesAdapter
    private lateinit var repository: SuperheroesRepository

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

        adapter = SuperheroesAdapter()

        val recyclerView = binding.rvSuperhero
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter





        // Observa los cambios en la lista paginada y actualiza el adaptador
        lifecycleScope.launch {
            viewModel.superheroes.collectLatest { pagingData ->
                adapter.submitData(pagingData)
                // Verifica si el último elemento es visible y llama a refreshSuperheroes si es necesario
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (lastVisibleItemPosition >= adapter.itemCount - 1) {
                    viewModel.refreshSuperheroes()
                }
            }
        }
    }
}
