package com.ogi.submission4.moviereview.presenter.tv

import android.content.Context
import com.google.gson.Gson
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.model.tv.TvResponse
import com.ogi.submission4.moviereview.network.api.TMDbApi
import com.ogi.submission4.moviereview.network.repository.MovieReviewRepository
import com.ogi.submission4.moviereview.utils.Network
import com.ogi.submission4.moviereview.view.TvShowView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TvShowPresenter(
    val context: Context,
    private val view: TvShowView,
    private val repository: MovieReviewRepository = MovieReviewRepository(),
    private  val gson: Gson = Gson(),
    private var sourceType: String = "main"
) {
    private val database = MovieReviewDB.getDatabase(context)
    fun getTvList(lang: String, query: String? = null){
        view.showLoading()
        var source = TMDbApi.getPopularTvShows(lang)
        if (sourceType == "search"){
            source = TMDbApi.searchTvShow(lang, query)
        }
        if (Network.isInternetAvailable(context))GlobalScope.launch(Dispatchers.Main) {
            val  data = gson.fromJson(
                repository.doRequestAsync(source).await(), TvResponse::class.java
            )
            if (data.results.isNotEmpty()){
                view.loadTvShow(data.results)
            }else{
                view.tvNotFound()
            }
            view.hideLoading()
        }
        else {
            view.noInternetConnection()
            view.hideLoading()
        }
    }

    fun getFavoriteTvList(): List<TvData>{
        return database.tvDao().all
    }

    fun getFavoriteTv(){
        view.showLoading()
        GlobalScope.launch(Dispatchers.Main) {
            val data = database.tvDao().all
            if (data.isNotEmpty()){
                view.loadTvShow(data)
            }else{
                view.tvNotFound()
            }
            view.hideLoading()
        }
    }
}