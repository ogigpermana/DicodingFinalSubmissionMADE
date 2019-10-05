package com.ogi.submission4.moviereview.view.activities.tv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.google.gson.Gson
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.TvShowAdapter
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.network.repository.MovieReviewRepository
import com.ogi.submission4.moviereview.presenter.tv.TvShowPresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_TV
import com.ogi.submission4.moviereview.utils.MovieReviewConst.TVSHOW_STATE
import com.ogi.submission4.moviereview.utils.getLanguageFormat
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.ogi.submission4.moviereview.view.TvShowView
import kotlinx.android.synthetic.main.activity_tv_show_search.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TvShowSearchActivity : AppCompatActivity(), TvShowView, SearchView.OnQueryTextListener {
    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.hide()
    }

    override fun loadTvShow(data: List<TvData>) {
        tvshows.clear()
        tvshows.addAll(data)
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        noInternet.hide()
    }

    override fun tvNotFound() {
        tvshows.clear()
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
            presenter.getTvList(lang.getLanguageFormat(), query)
        }else{
            tvshows.clear()
            adapter.notifyDataSetChanged()
        }
        return true
    }

    private var tvshows: MutableList<TvData> = mutableListOf()
    private lateinit var presenter: TvShowPresenter
    private lateinit var adapter: TvShowAdapter
    private lateinit var lang: String
    private lateinit var menuItem: MenuItem
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show_search)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.search_hint_tv)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        presenter = TvShowPresenter(this, this, MovieReviewRepository(), Gson(), "search")

        adapter = TvShowAdapter(baseContext, tvshows, presenter.getFavoriteTvList()){
            startActivity<TvShowDetailActivity>(FLAG_TV to it)
        }

        lang = Locale.getDefault().language

        rv_tvshows.layoutManager = LinearLayoutManager(this)
        rv_tvshows.adapter = adapter

        if (savedInstanceState != null){
            val saved: ArrayList<TvData> = savedInstanceState.getParcelableArrayList(TVSHOW_STATE)
            loadTvShow(saved.toList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        menuItem = menu.findItem(R.id.search_id)

        searchView = menuItem.actionView as SearchView
        searchView.isIconified = false
        searchView.queryHint = getString(R.string.search_hint_tv)
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
