package com.yerayyas.pagingmarvelapi.presentation.adapters


import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.squareup.picasso.Picasso
import com.yerayyas.pagingmarvelapi.R
import com.yerayyas.pagingmarvelapi.data.model.SuperheroItemResponse
import com.yerayyas.pagingmarvelapi.databinding.ItemSuperheroBinding

class SuperheroesAdapter : PagingDataAdapter<SuperheroItemResponse, SuperheroesAdapter.SuperheroListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroListViewHolder {
        return SuperheroListViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_superhero, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SuperheroListViewHolder, position: Int) {
        val superhero = getItem(position)
        if (superhero != null) {
            holder.bind(superhero)
        }

    }

    inner class SuperheroListViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemSuperheroBinding.bind(itemView)

        fun bind(superhero: SuperheroItemResponse) {
            // Superhero name
            binding.tvSuperheroName.text = superhero.name

            // Superhero image
            val imageUrl = "${superhero.thumbnail.path}.${superhero.thumbnail.extension}"
            val context = binding.root.context

            Log.d("CHURRO", "Loading image from URL: $imageUrl") // Agrega este log
            Picasso.get().invalidate(imageUrl)
            if (imageUrl.contains("image_not_available")) {
                Log.d("CHURRO", "Loading default image") // Agrega este log
                Picasso.get().load(R.drawable.marvel_image_not_found).into(binding.ivSuperhero)
            } else {
                Log.d("CHURRO", "Loading final image")
                Picasso.get().load(imageUrl).into(binding.ivSuperhero)
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SuperheroItemResponse>() {
            override fun areItemsTheSame(oldItem: SuperheroItemResponse, newItem: SuperheroItemResponse): Boolean {
                return oldItem.superheroId == newItem.superheroId
            }

            override fun areContentsTheSame(oldItem: SuperheroItemResponse, newItem: SuperheroItemResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}
