package com.ogi.submission4.moviereview.model.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.ogi.submission4.moviereview.model.database.dao.MovieDao
import com.ogi.submission4.moviereview.model.database.dao.TvDao
import com.ogi.submission4.moviereview.model.movie.entity.MovieData
import com.ogi.submission4.moviereview.model.tv.entity.TvData

const val dbVersion = 1

@Database(entities = [MovieData::class, TvData::class], version = dbVersion, exportSchema = false)
abstract class MovieReviewDB: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao

    companion object{
        private const val DB_NAME = "movie_catalogue_db"
        private var dbInstance: MovieReviewDB? = null

        fun getDatabase(context: Context): MovieReviewDB{
            if (dbInstance == null){
                dbInstance = Room
                    .databaseBuilder(context.applicationContext, MovieReviewDB::class.java, DB_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return dbInstance as MovieReviewDB
        }
    }
}