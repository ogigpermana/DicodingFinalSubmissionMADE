package com.ogi.submission4.moviereview.view.activities.info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.ogi.submission4.moviereview.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.about)
        about_app.loadUrl("file:///android_asset/about.html")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            this.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
