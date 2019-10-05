package com.ogi.submission4.moviereview.utils

import android.view.View


fun String.getYear(): String {
    return this.substring(0,4)
}

fun String.getLanguageFormat(): String {
    if(this == "in")
        return "id-ID"
    return "${this}-${this.toUpperCase()}"
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}