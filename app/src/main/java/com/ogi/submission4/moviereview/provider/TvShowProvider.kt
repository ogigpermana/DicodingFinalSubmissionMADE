package com.ogi.submission4.moviereview.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.ogi.submission4.moviereview.R
import com.ogi.submission4.moviereview.model.database.MovieReviewDB
import com.ogi.submission4.moviereview.model.tv.entity.TvData

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TvShowProvider : ContentProvider() {
    private val authorityTV = "com.ogi.submission4.moviereview"
    private val tvs = 1
    private val tvItem = 2
    private lateinit var database: MovieReviewDB
    private val matcher = UriMatcher(UriMatcher.NO_MATCH)

    private val uriMatcher = matcher.apply {
        addURI(authorityTV, "tv_shows", tvs)
        addURI(authorityTV, "tv_shows/#", tvItem)
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        when(matcher.match(uri)){
            tvs -> {
                if (context == null) {
                    return null
                }
                val id = database.tvDao().insert(TvData.fromContentValues(values))
                context?.contentResolver?.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            tvItem -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if (context == null){
            return null
        }
        val cursor: Cursor?
        val favoriteTvShows = database.tvDao()
        val code = matcher.match(uri)
        if (code in tvs..tvItem){
            cursor = when(uriMatcher.match(uri)){
                tvs -> favoriteTvShows.allFavorite()
                tvItem -> favoriteTvShows.getFavoriteById(ContentUris.parseId(uri))
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
            tvs -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            tvItem -> {
                if(context == null) return 0
                val tv = TvData.fromContentValues(values)
                tv.id = ContentUris.parseId(uri).toInt()
                val count = database.tvDao().update(tv)
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri,  selection: String?, selectionArgs: Array<String>?): Int {
        when(matcher.match(uri)){
            tvs -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            tvItem -> {
                if (context == null) {
                    return 0
                }
                val count = database.tvDao().deleteById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when(matcher.match(uri)) {
            tvs -> "vnd.android.cursor.dir/$authorityTV.tv_shows"
            tvItem -> "vnd.android.cursor.item/$authorityTV.tv_shows"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}