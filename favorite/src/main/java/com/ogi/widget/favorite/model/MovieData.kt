package com.ogi.widget.favorite.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

class MovieData(
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    var originalTitle: String? = null,

    @ColumnInfo(name = "overview")
    var overview: String? = null,

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    var voteAverage: Float = 0f,

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    var releaseDate: String? = null

) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readFloat(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeString(originalTitle)
        writeString(overview)
        writeFloat(voteAverage)
        writeString(posterPath)
        writeString(backdropPath)
        writeString(releaseDate)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MovieData> = object : Parcelable.Creator<MovieData> {
            override fun createFromParcel(source: Parcel): MovieData = MovieData(source)
            override fun newArray(size: Int): Array<MovieData?> = arrayOfNulls(size)
        }
    }
}