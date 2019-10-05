package com.ogi.submission4.moviereview.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.adapter.CustomPageAdapter
import kotlinx.android.synthetic.main.fragment_favorite.view.*

class FavoriteFragment : Fragment() {
    private lateinit var rootView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view?.let { super.onViewCreated(it, savedInstanceState) }
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.favorites_title)
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_favorite, container, false)
        rootView.view_pager.adapter = CustomPageAdapter(requireFragmentManager(), requireContext())
        rootView.tabs.setupWithViewPager(rootView.view_pager)
        return rootView
    }


}
