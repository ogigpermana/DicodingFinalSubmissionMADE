package com.ogi.submission4.moviereview.presenter.tv

import android.content.Context
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.view.OptionsFavoriteView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowDetailPresenter(
    var context: Context
) {
    private val db = MovieReviewDB.getDatabase(context)
    val view: OptionsFavoriteView = context as OptionsFavoriteView

    fun addToFavorite(tvshow: TvData){
        GlobalScope.launch(Dispatchers.Main) {
            db.tvDao().insert(tvshow)
            view.onFavorited()
        }
    }

    fun removeFromFavorite(tvshow: TvData){
        GlobalScope.launch(Dispatchers.Main) {
            db.tvDao().delete(tvshow)
            view.onUnFavorited()
        }
    }

    @Suppress("SENSELESS_COMPARISON")
    fun isFavorited(tvshow: TvData): Boolean{
        return db.tvDao().getById(tvshow.id) != null
    }
}