package com.example.com

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ExampleJobService : JobService() {

    private val TAG = "ExampleJobService"
    private var jobCancelled = false

    override fun onStopJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        jobCancelled = true
        return true
    }

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job started")
        doBackgroundWork(params)
        return true
    }

    private fun doBackgroundWork(params: JobParameters) {
        Thread(Runnable {
            for (i in 0..9) {
                Log.d(TAG, "run: $i")
                if (jobCancelled) {
                    return@Runnable
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Log.d(TAG, "Job finished")
            jobFinished(params, false)
        }).start()
    }

}