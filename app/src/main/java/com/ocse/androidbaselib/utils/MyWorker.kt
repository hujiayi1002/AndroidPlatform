package com.ocse.androidbaselib.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(context: Context, workerParams: WorkerParameters): Worker(context,workerParams) {
   private var count=0
    override fun doWork(): Result {
        Log.e("TAG", "doWork${count++}")
        return Result.success()
    }
}