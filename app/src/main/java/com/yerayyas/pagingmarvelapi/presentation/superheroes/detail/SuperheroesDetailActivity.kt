package com.yerayyas.pagingmarvelapi.presentation.superheroes.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.yerayyas.pagingmarvelapi.R
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiManager
import com.yerayyas.pagingmarvelapi.data.api.MarvelApiService
import com.yerayyas.pagingmarvelapi.data.model.ImageDatasResult
import com.yerayyas.pagingmarvelapi.databinding.ActivitySuperheroesDetailBinding
import com.yerayyas.pagingmarvelapi.utils.Constants.API_KEY
import com.yerayyas.pagingmarvelapi.utils.Constants.HASH
import com.yerayyas.pagingmarvelapi.utils.Constants.TS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit


class SuperheroesDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivitySuperheroesDetailBinding
    private lateinit var retrofit: Retrofit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = MarvelApiManager.retrofit

        val id: Int = intent.getIntExtra(EXTRA_ID, -1)
        getSuperheroInformation(id)
    }

    private fun getSuperheroInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail =
                retrofit.create(MarvelApiService::class.java)
                    .getSuperheroesDetail(id, API_KEY, HASH, TS)

            val response = superheroDetail.body()
            if (response != null) {
                runOnUiThread{
                    createUI(response)
                }
            }
        }
    }

    private fun createUI(superhero: ImageDatasResult) {
        val thumbnail = superhero.data.results[0].thumbnail
        val imageUrl = "${thumbnail.path}.${thumbnail.extension}"
        if (imageUrl.contains("image_not_available")) {
            Picasso.get().load(R.drawable.marvel_image_not_found).into(binding.ivSuperhero)
        } else {
            Picasso.get().load(imageUrl).into(binding.ivSuperhero)
        }
        binding.tvSuperheroName.text = superhero.data.results[0].name
        binding.tvSuperheroDescription.text = superhero.data.results[0].description
    }
}