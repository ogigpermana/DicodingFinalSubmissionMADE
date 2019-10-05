package com.ogi.submission4.moviereview.model.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import android.database.Cursor
import com.ogi.submission4.moviereview.model.tv.entity.TvData

@Dao
interface TvDao {
    @get:Query("SELECT * FROM tv_shows")
    val all: List<TvData>

    @Query("SELECT * FROM tv_shows WHERE id = :id")
    fun getById(id: Int?): TvData

    @Insert(onConflict = REPLACE)
    fun insert(tv_show: TvData): Long

    @Delete
    fun delete(tv_show: TvData)

    @Query("DELETE FROM tv_shows WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("SELECT * FROM tv_shows")
    fun allFavorite(): Cursor

    @Query("SELECT * FROM tv_shows WHERE id = :id")
    fun getFavoriteById(id: Long): Cursor

    @Update
    fun update(tv_show: TvData): Int
}