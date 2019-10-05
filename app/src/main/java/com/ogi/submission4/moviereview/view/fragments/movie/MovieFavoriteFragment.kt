package com.ogi.submission4.moviereview.view.fragments.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.MovieAdapter
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.presenter.movie.MoviePresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_MOVIE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.LANG_STATE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.STATE
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.ogi.submission4.moviereview.view.MovieView
import com.ogi.submission4.moviereview.view.activities.movie.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieFavoriteFragment : Fragment(), MovieView {

    private var movies: MutableList<MovieData> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: MoviePresenter
    private lateinit var adapter: MovieAdapter
    private lateinit var lang: String

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteMovie()
    }

    override fun showLoading() {
        rootView.progress.show()
    }

    override fun hideLoading() {
        rootView.progress.hide()
    }

    override fun loadMovie(data: List<MovieData>) {
        movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
        rootView.tvDataNotFound.hide()
    }

    override fun movieNotFound() {
        movies.clear()
        adapter.notifyDataSetChanged()
        rootView.tvDataNotFound.show()
    }

    override fun noInternetConnection() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_movie, container, false)
        presenter = MoviePresenter(requireContext(), this)
        adapter = MovieAdapter(rootView.context, movies, presenter.getFavoriteMovieList()){
            startActivity<MovieDetailActivity>(FLAG_MOVIE to it)
        }
        lang = Locale.getDefault().language
        rootView.rv_movies.layoutManager = LinearLayoutManager(activity)
        rootView.rv_movies.adapter = adapter

        val oldLanguage = savedInstanceState?.getString(LANG_STATE)
        if (savedInstanceState != null && oldLanguage == lang){
            val saved: ArrayList<MovieData> = savedInstanceState.getParcelableArrayList(STATE)
            loadMovie(saved.toList())
        }else
            presenter.getFavoriteMovie()
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE, ArrayList<MovieData>(movies))
        outState.putString(LANG_STATE, lang)
    }

}
