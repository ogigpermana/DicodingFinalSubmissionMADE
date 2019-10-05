package com.ogi.submission4.moviereview.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.view.viewholder.TvViewHolder

class TvShowAdapter(
    private val context: Context,
    private var tvshows: List<TvData>,
    private var favorites: List<TvData>,
    private val listener: (TvData) -> Unit
): RecyclerView.Adapter<TvViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        return TvViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tv_list, parent, false))
    }

    override fun getItemCount(): Int {
        return tvshows.size
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        holder.bindItem(tvshows[position], favorites, listener)
    }
}