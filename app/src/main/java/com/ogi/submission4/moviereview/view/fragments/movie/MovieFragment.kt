package com.ogi.submission4.moviereview.view.fragments.movie


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.MovieAdapter
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.presenter.movie.MoviePresenter
import com.ogi.submission4.moviereview.utils.MovieReviewConst.FLAG_MOVIE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.LANG_STATE
import com.ogi.submission4.moviereview.utils.MovieReviewConst.MOVIE_STATE
import com.ogi.submission4.moviereview.utils.getLanguageFormat
import com.ogi.submission4.moviereview.utils.hide
import com.ogi.submission4.moviereview.utils.show
import com.ogi.submission4.moviereview.view.MovieView
import com.ogi.submission4.moviereview.view.activities.movie.MovieDetailActivity
import com.ogi.submission4.moviereview.view.activities.movie.MovieSearchActivity
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieFragment : Fragment(), MovieView {

    private var movies: MutableList<MovieData> = arrayListOf()
    private lateinit var rootView: View
    private lateinit var presenter: MoviePresenter
    private lateinit var adapter: MovieAdapter
    private lateinit var lang: String

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
        rootView.noInternet.hide()
    }

    override fun movieNotFound() {
        movies.clear()
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
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.movie_title)
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
            val saved: ArrayList<MovieData> = savedInstanceState.getParcelableArrayList(MOVIE_STATE)
            loadMovie(saved.toList())
        }else{
            presenter.getMovieList(lang.getLanguageFormat())
        }
        return rootView
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MOVIE_STATE, ArrayList<MovieData>(movies))
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
                startActivity<MovieSearchActivity>()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }
}
