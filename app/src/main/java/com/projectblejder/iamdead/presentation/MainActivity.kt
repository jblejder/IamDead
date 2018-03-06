package com.projectblejder.iamdead.presentation

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import com.projectblejder.iamdead.R
import com.projectblejder.iamdead.databinding.ActivityMainBinding
import com.projectblejder.iamdead.infrastructure.MyJobService
import com.projectblejder.iamdead.presentation.base.Binding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var jobScheduler: JobScheduler
    lateinit var sharedPreferences: SharedPreferences

    val handler = Handler(Looper.getMainLooper())

    @Inject
    lateinit var viewModel: MainActivityViewModel
    val binding = Binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding.setContentView(this)

        jobScheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        sharedPreferences = getSharedPreferences("job", Context.MODE_PRIVATE)


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

    }
}


