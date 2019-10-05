package com.ogi.submission4.moviereview.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.view.viewholder.MovieViewHolder

class MovieAdapter (
    private val context: Context,
    private var movies: List<MovieData>,
    private var favorites: List<MovieData>,
    private val listener: (MovieData) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(movies[position], favorites, listener)
    }
}