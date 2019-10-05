package com.ogi.submission4.moviereview.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie_list.view.*

class MovieViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(movie: MovieData, favorites: List<MovieData>, listener: (MovieData) -> Unit){
        Picasso.get()
            .load(MovieReviewConst.IMG_URL+movie.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(itemView.iv_poster_path)
        itemView.tv_title.text = movie.originalTitle
        itemView.tv_release_date.text = movie.releaseDate
        itemView.rb_rating.rating = movie.voteAverage/2
        itemView.tv_vote_average.text = movie.voteAverage.toString()
        itemView.tv_overview.text = movie.overview

        if (favorites.any { it.id == movie.id })
            itemView.iv_favorite.show()
        else itemView.iv_favorite.hide()

        itemView.setOnClickListener { listener(movie) }
    }
}