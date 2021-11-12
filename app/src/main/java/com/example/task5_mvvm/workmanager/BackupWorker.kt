package com.example.task5_mvvm.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.task5_mvvm.constants.BACKUP_WORKER_END
import com.example.task5_mvvm.constants.BACKUP_WORKER_SLEEP_TIME
import com.example.task5_mvvm.constants.BACKUP_WORKER_START
import com.example.task5_mvvm.constants.BACKUP_WORKER_SUCCESS
import java.util.concurrent.TimeUnit

/**
 *
 * Класс для фонового выполнения задач
 * В данном случае вывод текста в LOG
 *
 */

class BackupWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        Log.d(TAG, BACKUP_WORKER_START)
        try {
            TimeUnit.SECONDS.sleep(BACKUP_WORKER_SLEEP_TIME);
            Log.d(TAG, BACKUP_WORKER_SUCCESS)
        } catch (e: Exception) {
            Log.d(TAG, e.message.toString())
            return Result.failure()
        }

        Log.d(TAG, BACKUP_WORKER_END)
        return Result.success()
    }

    companion object {
        const val TAG = "WORK_MANAGER"
    }
}