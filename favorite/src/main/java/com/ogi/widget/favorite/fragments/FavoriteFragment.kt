package com.ogi.widget.favorite.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ogi.widget.favorite.ui.movie.MovieActivity

import com.ogi.widget.favorite.R
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {
    private lateinit var rooView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.let { super.onViewCreated(it, savedInstanceState) }
        (activity as AppCompatActivity).supportActionBar?.title = "Menu Favorites"
        // Inflate the layout for this fragment
        rooView = inflater.inflate(R.layout.fragment_favorite, container, false)
        rooView.btnMovie.setOnClickListener {
            val listMovie = Intent(context, MovieActivity::class.java)
            startActivity(listMovie)
        }
        rooView.btnTv.setOnClickListener {
//            val listTvShow = Intent(context, TvShowActivity::class.java)
//            startActivity(listTvShow)
            Snackbar.make(
                rooView, // Parent view
                "TvShow Favorite under construction :D", // Message to show
                Snackbar.LENGTH_SHORT // How long to display the message.
            ).show()
        }
        return rooView
    }

}
