package com.dreamsoftware.runtracker

import android.app.Application
import com.dreamsoftware.runtracker.utils.IApplicationAware
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RunTrackerApp : Application(), IApplicationAware {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}