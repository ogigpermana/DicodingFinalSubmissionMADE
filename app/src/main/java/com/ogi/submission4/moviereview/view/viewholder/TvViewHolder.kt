package com.ogi.submission4.moviereview.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tv_list.view.*

class TvViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bindItem(tv: TvData, favorites: List<TvData>, listener: (TvData) -> Unit){
        Picasso.get()
            .load(MovieReviewConst.IMG_URL+tv.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(itemView.iv_poster_path)
        itemView.tv_original_name.text = tv.originalName
        itemView.tv_first_air.text = tv.releaseDate
        itemView.rb_rating.rating = tv.voteAverage/2
        itemView.tv_vote_average.text = tv.voteAverage.toString()
        itemView.tv_overview.text = tv.overview

        if (favorites.any { it.id == tv.id })
            itemView.iv_favorite.show()
        else itemView.iv_favorite.hide()

        itemView.setOnClickListener { listener(tv) }
    }
}