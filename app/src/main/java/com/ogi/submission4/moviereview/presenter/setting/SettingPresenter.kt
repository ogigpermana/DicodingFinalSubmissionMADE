package com.ogi.submission4.moviereview.presenter.setting

import android.content.Context
import com.google.gson.Gson
import com.ogi.submission4.moviereview.model.movie.MovieResponse
import com.ogi.submission4.moviereview.network.api.TMDbApi
import com.ogi.submission4.moviereview.network.repository.MovieReviewRepository
import com.ogi.submission4.moviereview.utils.Network
import com.ogi.submission4.moviereview.view.settings.SettingCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SettingPresenter(
    val context: Context,
    private val view: SettingCallback,
    private val movieReviewRepository: MovieReviewRepository = MovieReviewRepository(),
    private val gson: Gson = Gson()
) {
    fun getNewReleaseMovies(date: String){
        if (Network.isInternetAvailable(context)){
            GlobalScope.launch(Dispatchers.Main) {
                val data = gson.fromJson(movieReviewRepository.doRequestAsync(TMDbApi.getNewRelease(date)).await(), MovieResponse::class.java)
                if (data.results.isNotEmpty()){
                    view.getNewReleaseMovieList(context, data.results)
                }
            }
        }
    }
}