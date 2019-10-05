package com.ogi.submission4.moviereview.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.movie.entity.MovieData

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MovieProvider: ContentProvider() {
    private val authorityMovie = "com.ogi.submission4.moviereview"
    private val movies = 1
    private val movieItem = 2
    private lateinit var database: MovieReviewDB
    private val matcher = UriMatcher(UriMatcher.NO_MATCH)

    private val uriMatcher = matcher.apply {
        addURI(authorityMovie, "movies", movies)
        addURI(authorityMovie, "movies/#", movieItem)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(matcher.match(uri)){
            movies -> {
                if (context == null) {
                    return null
                }
                val id = database.movieDao().insert(MovieData.fromContentValues(values))
                context?.contentResolver?.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            movieItem -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if (context == null){
            return null
        }
        val cursor: Cursor?
        val favoriteMovies = database.movieDao()
        val code = matcher.match(uri)
        if (code in movies..movieItem){
            cursor = when(uriMatcher.match(uri)){
                movies -> favoriteMovies.allFavorite()
                movieItem -> favoriteMovies.getFavoriteById(ContentUris.parseId(uri))
                else -> {
                    null
                }
            }
            cursor?.setNotificationUri(context.contentResolver, uri)
            return cursor
        }else throw IllegalArgumentException(context.getString(R.string.unknown_uri, uri))
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onCreate(): Boolean {
        database = MovieReviewDB.getDatabase(context)
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        when(matcher.match(uri)){
            movies -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            movieItem -> {
                if(context == null) return 0
                val movie = MovieData.fromContentValues(values)
                movie.id = ContentUris.parseId(uri).toInt()
                val count = database.movieDao().update(movie)
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri,  selection: String?, selectionArgs: Array<String>?): Int {
        when(matcher.match(uri)){
            movies -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            movieItem -> {
                if (context == null) return 0
                val count = database.movieDao().deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when(matcher.match(uri)) {
            movies -> "vnd.android.cursor.dir/$authorityMovie.movies"
            movieItem -> "vnd.android.cursor.item/$authorityMovie.movies"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}