package com.ogi.submission4.moviereview.model.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import android.database.Cursor
import com.ogi.submission4.moviereview.model.movie.entity.MovieData

@Dao
interface MovieDao {
    @get:Query("SELECT * FROM movies")
    val all: List<MovieData>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getById(id: Int?): MovieData

    @Insert(onConflict = REPLACE)
    fun insert(movie: MovieData): Long

    @Delete
    fun delete(movie: MovieData)

    @Query("DELETE FROM movies WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM movies")
    fun allFavorite(): Cursor

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getFavoriteById(id: Long): Cursor

    @Update
    fun update(movie: MovieData): Int
}