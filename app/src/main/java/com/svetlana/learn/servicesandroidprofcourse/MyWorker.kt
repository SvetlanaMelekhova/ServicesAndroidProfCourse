package com.svetlana.learn.servicesandroidprofcourse

import android.content.Context
import android.util.Log
import androidx.work.*
import kotlin.time.measureTime

class MyWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {


    override fun doWork(): Result {
        log("doWork")
        val page = workerParameters.inputData.getInt(PAGE, 0)
        for (i in 0 until 20) {
            Thread.sleep(1000)
            log("Timer $i")
        }
        return Result.success()
    }

    private fun log(message: String) {
        Log.d("SERVICE_TAG", "MyForegroundService: $message")
    }

    companion object {
        private const val PAGE = "page"
        const val WORK_NAME = "work name"

        fun makeRequest(page: Int): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(workDataOf(PAGE to page))
                .setConstraints(makeConstrains())
                .build()
        }

        private fun makeConstrains(): Constraints {
            return  Constraints.Builder()
                .setRequiresCharging(true)
                .build()
        }
    }
}