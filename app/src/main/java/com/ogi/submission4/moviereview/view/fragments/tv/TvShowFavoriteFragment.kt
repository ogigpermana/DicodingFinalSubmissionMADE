package com.ogi.submission4.moviereview.view.fragments.tv


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.TvShowAdapter
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.presenter.tv.TvShowPresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_TV
import com.ogi.submission4.moviereview.utils.MovieReviewConst.LANG_STATE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.STATE
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.ogi.submission4.moviereview.view.TvShowView
import com.ogi.submission4.moviereview.view.activities.tv.TvShowDetailActivity
import kotlinx.android.synthetic.main.fragment_movie.view.progress
import kotlinx.android.synthetic.main.fragment_movie.view.tvDataNotFound
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import org.jetbrains.anko.support.v4.startActivity
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TvShowFavoriteFragment : Fragment(), TvShowView {

    private var tvshows: MutableList<TvData> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: TvShowPresenter
    private lateinit var adapter: TvShowAdapter
    private lateinit var lang: String

    override fun onResume() {
        super.onResume()
        presenter.getFavoriteTv()
    }
    override fun showLoading() {
        rootView.progress.show()
    }

    override fun hideLoading() {
        rootView.progress.hide()
    }

    override fun loadTvShow(data: List<TvData>) {
        tvshows.clear()
        tvshows.addAll(data)
        adapter.notifyDataSetChanged()
        rootView.tvDataNotFound.hide()
    }

    override fun tvNotFound() {
        tvshows.clear()
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
        rootView = inflater.inflate(R.layout.fragment_tv_show, container, false)
        presenter = TvShowPresenter(requireContext(), this)
        adapter = TvShowAdapter(rootView.context, tvshows, presenter.getFavoriteTvList()){
            startActivity<TvShowDetailActivity>(FLAG_TV to it)
        }
        lang = Locale.getDefault().language
        rootView.rv_tvshows.layoutManager = LinearLayoutManager(activity)
        rootView.rv_tvshows.adapter = adapter

        val oldLanguage = savedInstanceState?.getString(LANG_STATE)
        if (savedInstanceState != null && oldLanguage == lang){
            val saved: ArrayList<TvData> = savedInstanceState.getParcelableArrayList(STATE)
            loadTvShow(saved.toList())
        }else{
            presenter.getFavoriteTv()
        }
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(STATE, ArrayList<TvData>(tvshows))
        outState.putString(LANG_STATE, lang)
    }
}
