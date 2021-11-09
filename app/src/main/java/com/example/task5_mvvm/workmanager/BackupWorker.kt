package com.example.task5_mvvm.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class BackupWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {

    override fun doWork(): Result {

        Log.d(TAG, "doWork start")
        try {
            TimeUnit.SECONDS.sleep(10);
            Log.d(TAG, "Success")
        }catch (e: Exception){
            Log.d(TAG, e.message.toString())
            return Result.failure()
        }

        Log.d(TAG, "doWork end")
        return Result.success()
    }

    companion object {
        const val TAG = "WORK_MANAGER"
    }
}