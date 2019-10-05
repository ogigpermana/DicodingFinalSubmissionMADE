package com.ogi.widget.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.model.TvData
import com.ogi.widget.favorite.viewholder.TvShowViewHolder

class TvShowAdapter(
    private val context: Context,
    private val tvshows: List<TvData>,
    private val listener: (TvData) -> Unit
) : RecyclerView.Adapter<TvShowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        return TvShowViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tv_show, parent, false))
    }

    override fun getItemCount(): Int {
        return tvshows.size
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        return holder.bind(tvshows[position], listener)
    }
}