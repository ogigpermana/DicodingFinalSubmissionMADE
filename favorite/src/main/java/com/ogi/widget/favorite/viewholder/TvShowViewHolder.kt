package com.ogi.widget.favorite.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.model.TvData
import com.ogi.widget.favorite.utils.FavoriteConst
import com.ogi.widget.favorite.utils.getYear
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_tv_show.view.*

class TvShowViewHolder(view: View): RecyclerView.ViewHolder(view) {
    fun bind(
        tvshow: TvData,
        listener: (TvData) -> Unit
    ){
        Picasso
            .get()
            .load(FavoriteConst.IMAGE_BASE_URL+tvshow.posterPath)
            .placeholder(R.drawable.default_poster_path)
            .error(R.drawable.broken_image)
            .into(itemView.iv_poster_path)
        itemView.tv_title.text = tvshow.originalName
        itemView.tv_release_date.text = tvshow.releaseDate?.getYear()
        itemView.rb_rating.rating = tvshow.voteAverage/2
        itemView.tv_vote_average.text = tvshow.voteAverage.toString()
        itemView.tv_overview.text = tvshow.overview

        itemView.setOnClickListener { listener(tvshow) }
    }
}