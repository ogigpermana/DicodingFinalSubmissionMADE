package com.ogi.submission4.moviereview.view.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.utils.MovieReviewConst
import com.squareup.picasso.Picasso

class MovieReviewRemoteViewsFactory(val context: Context): RemoteViewsService.RemoteViewsFactory {
    private var mWidgetItem: MutableList<MovieData> = mutableListOf()
    private lateinit var presenterReview:  MovieReviewWidgetPresenter

    override fun onCreate() {
        presenterReview = MovieReviewWidgetPresenter(context)
        mWidgetItem.addAll(presenterReview.getListFavoriteMovies())
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun onDataSetChanged() {
        mWidgetItem.clear()
        mWidgetItem.addAll(presenterReview.getListFavoriteMovies())
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getViewAt(position: Int): RemoteViews {
        val movie = mWidgetItem[position]
        val remoteView = RemoteViews(context.packageName, R.layout.widget_item)
        val bitmap = Picasso
            .get()
            .load(MovieReviewConst.IMG_URL+movie.backdropPath)
            .get()
        remoteView.setImageViewBitmap(R.id.imageView, bitmap)
        remoteView.setTextViewText(R.id.movieTitle, movie.originalTitle)
        val extras = Bundle()
        extras.putInt(MovieReviewWidget.EXTRA_ITEM, position)
        val fillIntent = Intent()
        fillIntent.putExtras(extras)
        return  remoteView
    }

    override fun getCount(): Int {
        return mWidgetItem.count()
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun onDestroy() {
    }
}