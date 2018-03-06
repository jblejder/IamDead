package com.projectblejder.iamdead

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    lateinit var startStopButton: Button
    lateinit var refreshButton: Button
    lateinit var status: TextView

    lateinit var jobScheduler: JobScheduler
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var emptyClassToInject: EmptyClassToInject

    val handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startStopButton = findViewById(R.id.startStopBtn)
        refreshButton = findViewById(R.id.refreshBtn)
        status = findViewById(R.id.status)
        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        sharedPreferences = getSharedPreferences("job", Context.MODE_PRIVATE)

        startStopButton.setOnClickListener { startStop() }
        refreshButton.setOnClickListener { refresh() }

        refresh()
    }

    fun startStop() {
        val running = jobScheduler.allPendingJobs.any { it.id == 0 }
        if (running) {
            stopTask()
        } else {
            createTask()
        }
    }

    private fun stopTask() {
        jobScheduler.cancel(0)
        handler.post { refresh() }
    }


    fun createTask() {
        val componentName = ComponentName(this, MyJobService::class.java)
        val jobInfo = JobInfo.Builder(0, componentName)
                .setPersisted(true)
                .setPeriodic(10_000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .build()

        jobScheduler.schedule(jobInfo)

        handler.post { refresh() }
    }

    fun refresh() {
        val stringBuilder = StringBuilder("Running services: ")
        jobScheduler.allPendingJobs.forEach { stringBuilder.append("${it.id} ") }
        stringBuilder.append("\n\n")

        sharedPreferences.getStringSet("set", emptySet()).forEach {
            stringBuilder.append("$it\n")
        }

        status.text = stringBuilder.toString()
    }
}


