package com.ogi.submission4.moviereview.network.repository

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL

class MovieReviewRepository {
    fun doRequestAsync(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}