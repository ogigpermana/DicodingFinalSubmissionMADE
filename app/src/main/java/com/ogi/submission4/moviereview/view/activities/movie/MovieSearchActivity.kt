package com.ogi.submission4.moviereview.view.activities.movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.google.gson.Gson
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.MovieAdapter
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.network.repository.MovieReviewRepository
import com.ogi.submission4.moviereview.presenter.movie.MoviePresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_MOVIE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.MOVIE_STATE
import com.ogi.submission4.moviereview.utils.getLanguageFormat
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.ogi.submission4.moviereview.view.MovieView
import kotlinx.android.synthetic.main.activity_movie_search.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieSearchActivity : AppCompatActivity(), MovieView, SearchView.OnQueryTextListener {

    private var movies: MutableList<MovieData> = mutableListOf()
    private lateinit var presenter: MoviePresenter
    private lateinit var adapter: MovieAdapter
    private lateinit var lang: String
    private lateinit var menuItem: MenuItem
    private lateinit var searchView: SearchView

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun loadMovie(data: List<MovieData>) {
        movies.clear()
        movies.addAll(data)
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        noInternet.hide()
    }

    override fun movieNotFound() {
        movies.clear()
        adapter.notifyDataSetChanged()
        tvDataNotFound.show()
    }

    override fun noInternetConnection() {
        noInternet.show()
        container.snackbar(getString(R.string.no_internet)).show()
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (query.length >= 3){
            presenter.getMovieList(lang.getLanguageFormat(), query)
        }else{
            movies.clear()
            adapter.notifyDataSetChanged()
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.search_hint_movie)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        presenter = MoviePresenter(this, this, MovieReviewRepository(), Gson(), "search")
        adapter = MovieAdapter(baseContext, movies, presenter.getFavoriteMovieList()){
            startActivity<MovieDetailActivity>(FLAG_MOVIE to it)
        }

        lang = Locale.getDefault().language
        rv_movies.layoutManager = LinearLayoutManager(this)
        rv_movies.adapter = adapter

        if(savedInstanceState !=  null){
            val saved: ArrayList<MovieData> = savedInstanceState.getParcelableArrayList(MOVIE_STATE)
            loadMovie(saved.toList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuItem = menu.findItem(R.id.search_id)

        searchView = menuItem.actionView as SearchView
        searchView.isIconified = false
        searchView.queryHint = getString(R.string.search_hint_movie)
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
           android.R.id.home -> {
                finish()
               true
            }else -> super.onOptionsItemSelected(item)
        }
    }
}
