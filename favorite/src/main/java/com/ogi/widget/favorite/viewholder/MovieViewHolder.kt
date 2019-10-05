package com.ogi.widget.favorite.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.model.MovieData
import com.ogi.widget.favorite.utils.FavoriteConst
import com.ogi.widget.favorite.utils.getYear
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(movie: MovieData, listener: (MovieData) -> Unit){
        Picasso
            .get()
            .load(FavoriteConst.IMAGE_BASE_URL+movie.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(itemView.iv_poster_path)
        itemView.tv_title.text = movie.originalTitle
        itemView.tv_release_date.text = movie.releaseDate?.getYear()
        itemView.rb_rating.rating = movie.voteAverage/2
        itemView.tv_vote_average.text = movie.voteAverage.toString()
        itemView.tv_overview.text = movie.overview

        itemView.setOnClickListener { listener(movie) }
    }
}