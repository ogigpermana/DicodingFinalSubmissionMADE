package com.ogi.widget.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.model.MovieData
import com.ogi.widget.favorite.viewholder.MovieViewHolder

class MovieAdapter(
    private val context: Context,
    private val movies: List<MovieData>,
    private val listener: (MovieData) -> Unit
    ) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position], listener)
    }
}