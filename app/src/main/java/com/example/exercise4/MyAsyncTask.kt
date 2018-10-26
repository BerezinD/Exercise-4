package com.example.exercise4

import android.os.AsyncTask

class MyAsyncTask(private val mInterfaceAsyncTask: InterfaceAsyncTask?): AsyncTask<Int, Int, String>(){

    override fun doInBackground(vararg argument: Int?): String {
        var start = 0
        if (argument.isNotEmpty()){
            if (argument[0] != null) {start = argument[0]!!}
        }
            while (start < 10) {
                start++
                if (isCancelled) return "Canceled"
                publishProgress(start)
                Thread.sleep(500)
            }
        return "Done!"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        mInterfaceAsyncTask?.onProgressUpdate(values[0])
    }

    override fun onPreExecute() {
        super.onPreExecute()
        mInterfaceAsyncTask?.onPreExecute()
    }

    override fun onCancelled(result: String?) {
        super.onCancelled()
        mInterfaceAsyncTask?.onCancel(result)
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        mInterfaceAsyncTask?.onPostExecute(result)
    }
}