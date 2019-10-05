package com.ogi.submission4.moviereview.view.fragments.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.TvShowAdapter
import com.ogi.submission4.moviereview.model.tv.entity.TvData
import com.ogi.submission4.moviereview.presenter.tv.TvShowPresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_TV
import com.ogi.submission4.moviereview.utils.MovieReviewConst.LANG_STATE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.TVSHOW_STATE
import com.ogi.submission4.moviereview.utils.getLanguageFormat
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.ogi.submission4.moviereview.view.TvShowView
import com.ogi.submission4.moviereview.view.activities.tv.TvShowDetailActivity
import com.ogi.submission4.moviereview.view.activities.tv.TvShowSearchActivity
import kotlinx.android.synthetic.main.fragment_tv_show.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TvShowFragment : Fragment(), TvShowView {

    private var tvshows: MutableList<TvData> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: TvShowPresenter
    private lateinit var adapter: TvShowAdapter
    private lateinit var lang: String

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
        rootView.noInternet.hide()
    }

    override fun tvNotFound() {
        tvshows.clear()
        adapter.notifyDataSetChanged()
        rootView.tvDataNotFound.show()
    }

    override fun noInternetConnection() {
        rootView.noInternet.show()
        rootView.snackbar(getString(R.string.no_internet)).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.let { super.onViewCreated(it, savedInstanceState) }
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.tv_shows_title)
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
            val saved: ArrayList<TvData> = savedInstanceState.getParcelableArrayList(TVSHOW_STATE)
            loadTvShow(saved.toList())
        }else{
            presenter.getTvList(lang.getLanguageFormat())
        }
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(TVSHOW_STATE, ArrayList<TvData>(tvshows))
        outState.putString(LANG_STATE, lang)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_button_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.search_id -> {
                startActivity<TvShowSearchActivity>()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}
