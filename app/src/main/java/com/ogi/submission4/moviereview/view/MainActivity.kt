package com.ogi.submission4.moviereview.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.FragmentTransaction
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.ogi.submission4.moviereview.view.fragments.FavoriteFragment
import com.ogi.submission4.moviereview.view.fragments.movie.MovieFragment
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.view.activities.info.AboutActivity
import com.ogi.submission4.moviereview.view.activities.settings.SettingActivity
import com.ogi.submission4.moviereview.view.fragments.tv.TvShowFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var movieFragment: MovieFragment
    private lateinit var tvShowFragment: TvShowFragment
    private lateinit var favoriteFragment: FavoriteFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.title = "Movie Review"

        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            (R.string.drawer_open),
            (R.string.drawer_close)){}
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //Set default fragment when app opened
        if (savedInstanceState == null){
            movieFragment = MovieFragment()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, movieFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }

    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.menu_movie -> {
                movieFragment = MovieFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, movieFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.menu_tv -> {
                tvShowFragment = TvShowFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, tvShowFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.menu_favorite -> {
                favoriteFragment = FavoriteFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, favoriteFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.menu_reminder -> {
                val intent = Intent(baseContext, SettingActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_locale_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
            R.id.menu_share ->{
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Movie Review.")
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
            R.id.menu_rating -> {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(
                        "https://play.google.com/store/apps/details?id=com.example.android")
                    setPackage("com.android.vending")
                }
                startActivity(intent)
            }
            R.id.menu_about -> {
                val intentAbout = Intent(baseContext, AboutActivity::class.java)
                startActivity(intentAbout)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.locale_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menu_locale_setting -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
