package com.projectblejder.iamdead.infrastructure

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import javax.inject.Inject

class JobDispatcher
@Inject constructor(
        private val context: Context
) {

    companion object {
        const val JOB_ID = 42
    }

    private val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

    val isRunning: Boolean
        get() {
            return jobScheduler.allPendingJobs.any { it.id == JOB_ID }
        }

    fun start() {
        if (isRunning) {
            return
        }
        val componentName = ComponentName(context, MyJobService::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, componentName)
                .setPersisted(true)
                .setPeriodic(10_000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build()

        jobScheduler.schedule(jobInfo)
    }

    fun stop() {
        if (!isRunning) {
            return
        }
        jobScheduler.cancel(JOB_ID)

    }
}
