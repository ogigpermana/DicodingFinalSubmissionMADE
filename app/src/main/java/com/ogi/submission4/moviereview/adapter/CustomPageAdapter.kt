package com.ogi.submission4.moviereview.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.view.fragments.movie.MovieFavoriteFragment
import com.ogi.submission4.moviereview.view.fragments.tv.TvShowFavoriteFragment

@Suppress("DEPRECATION")
class CustomPageAdapter(fm : FragmentManager, private val context: Context) : FragmentStatePagerAdapter(fm) {
    private val pages = listOf(
        MovieFavoriteFragment(),
        TvShowFavoriteFragment()
    )
    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> context.getString(R.string.movie_title)
            1 -> context.getString(R.string.tv_shows_title)
            else -> context.getString(R.string.movie_title)
        }
    }
}