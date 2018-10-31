package com.example.exercise4

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log

abstract class SimpleOfThread {

    private var mBackgroundThread: Thread? = null
    @Volatile var mInterrupted: Boolean = false

    protected abstract fun onPreExecute()

    protected abstract fun doInBackground()

    protected abstract fun onPostExecute()

    protected abstract fun onProgressUpdate()

    protected fun publishProgress() {
        Log.d(TAG, "publish progress")
        runOnUiThread(Runnable {
            onProgressUpdate()
        })
    }

    fun execute(){
        runOnUiThread(Runnable {
            Log.d(TAG, "new Thread")
            onPreExecute()
            mBackgroundThread = object:Thread("Handler_executor_thread") {
                override fun run() {
                    Log.d(TAG, "RUN!")
                    doInBackground()
                    runOnUiThread(Runnable { onPostExecute() })
                }
            }
            mBackgroundThread?.start()
            Log.d(TAG, "start Thread")
        })
    }

    private fun runOnUiThread(runnable: Runnable){
        Handler(Looper.getMainLooper()).post(runnable)
    }

    fun cancel(){
        Log.d(TAG, "on cancel called")
        mInterrupted = true
        if (mBackgroundThread != null){
            mBackgroundThread?.interrupt()
        }
    }
}