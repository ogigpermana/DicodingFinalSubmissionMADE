package com.ogi.widget.favorite.utils

import android.database.Cursor
import com.ogi.widget.favorite.model.MovieData
import com.ogi.widget.favorite.model.TvData

class FavoriteCursorHelper {
    companion object{
        fun favCursorToListMovie(cursor: Cursor): List<MovieData>{
            val movies = mutableListOf<MovieData>()

            while (cursor.moveToNext()){
                val movie = MovieData(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("original_title")),
                    cursor.getString(cursor.getColumnIndexOrThrow("overview")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("vote_average")),
                    cursor.getString(cursor.getColumnIndexOrThrow("poster_path")),
                    cursor.getString(cursor.getColumnIndexOrThrow("backdrop_path")),
                    cursor.getString(cursor.getColumnIndexOrThrow("release_date"))
                )
                movies.add(movie)
            }
            return movies
        }
        fun favCursorToListTv(cursor: Cursor) : List<TvData>{
            val tvshows = mutableListOf<TvData>()
            while (cursor.moveToNext()){
                 val tvshow = TvData(
                     cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                     cursor.getString(cursor.getColumnIndexOrThrow("original_name")),
                     cursor.getString(cursor.getColumnIndexOrThrow("overview")),
                     cursor.getFloat(cursor.getColumnIndexOrThrow("vote_average")),
                     cursor.getString(cursor.getColumnIndexOrThrow("poster_path")),
                     cursor.getString(cursor.getColumnIndexOrThrow("backdrop_path")),
                     cursor.getString(cursor.getColumnIndexOrThrow("first_air_date"))
                 )
                tvshows.add(tvshow)
            }
            return tvshows
        }
    }
}