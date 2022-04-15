package com.svetlana.learn.servicesandroidprofcourse

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobWorkItem
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.svetlana.learn.servicesandroidprofcourse.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.simpleService.setOnClickListener {
            stopService(MyForegroundService.newIntent(this))
            //startService(MyService.newIntent(this))
        }

        binding.foregroundService.setOnClickListener {
            ContextCompat.startForegroundService(this, MyForegroundService.newIntent(this))
        }

        binding.intentService.setOnClickListener {
            ContextCompat.startForegroundService(this, MyIntentService.newIntent(this))
        }

        //since api 29 doesn't work with setRequiresCharging and setRequiredNetworkType
        binding.jobDispatcher.setOnClickListener {
            val componentName = ComponentName(this, MyJobServiceEnqueue::class.java)

            val jobInfo = JobInfo.Builder(MyJobServiceEnqueue.JOB_ID, componentName)
                //.setExtras(MyJobService.newBundle(page++))
                //.setRequiresCharging(true)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .build()

            val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            //jobScheduler.schedule(jobInfo)
            val intent = MyJobServiceEnqueue.newIntent(page++)
            jobScheduler.enqueue(jobInfo, JobWorkItem(intent))
        }

        binding.jobIntentService.setOnClickListener {
            MyJobIntentService.enqueue(this, page++)
        }
    }
}