package com.projectblejder.iamdead.infrastructure

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import com.projectblejder.iamdead.shared.extensions.inEdit
import org.joda.time.DateTime

class MyJobService : JobService() {

    override fun onStopJob(p0: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        val preferences = applicationContext.getSharedPreferences("job", Context.MODE_PRIVATE)
        preferences.inEdit {
            val set = preferences.getStringSet("set", emptySet()).toMutableSet()
            set.add(DateTime.now().toString())
            putStringSet("set", set)
        }
        jobFinished(p0, false)
        return false
    }
}