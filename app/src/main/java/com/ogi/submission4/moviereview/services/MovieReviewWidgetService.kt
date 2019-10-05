package com.ogi.submission4.moviereview.services

import android.content.Intent
import android.widget.RemoteViewsService
import com.ogi.submission4.moviereview.view.widgets.MovieReviewRemoteViewsFactory

class MovieReviewWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MovieReviewRemoteViewsFactory(this.applicationContext)
    }
}