package com.ogi.widget.favorite.ui.tv

import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.ogi.widget.favorite.R
import com.ogi.widget.favorite.adapter.TvShowAdapter
import com.ogi.widget.favorite.model.TvData
import com.ogi.widget.favorite.utils.FavoriteConst.AUTHORITY_TV
import com.ogi.widget.favorite.utils.FavoriteConst.FLAG_TV_SHOW
import com.ogi.widget.favorite.utils.FavoriteConst.TABLE_TV
import com.ogi.widget.favorite.utils.FavoriteCursorHelper
import com.ogi.widget.favorite.utils.hide
import com.ogi.widget.favorite.utils.show
import kotlinx.android.synthetic.main.activity_tv_show.*
import org.jetbrains.anko.startActivity

@Suppress("DEPRECATION")
class TvShowActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Cursor> {
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        progress.show()
        return CursorLoader(this, contentUri, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor) {
        mTvData.clear()
        mTvData.addAll(FavoriteCursorHelper.favCursorToListTv(cursor))
        adapter.notifyDataSetChanged()
        tvDataNotFound.hide()
        progress.hide()
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        mTvData.clear()
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        supportLoaderManager.restartLoader(100, null, this)
    }

    private val mTvData : MutableList<TvData> = mutableListOf()
    private lateinit var adapter: TvShowAdapter

    private val contentUri = Uri.Builder()
        .scheme("content")
        .authority(AUTHORITY_TV)
        .appendPath(TABLE_TV)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_show)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val actionBar = supportActionBar
        actionBar?.title = "Tv Shows"

        adapter = TvShowAdapter(this, mTvData){
            startActivity<TvShowDetailActivity>(FLAG_TV_SHOW to it)
        }

        rv_tv_shows.layoutManager = LinearLayoutManager(this)
        rv_tv_shows.adapter = adapter

        supportLoaderManager.initLoader(100, null, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            this.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
