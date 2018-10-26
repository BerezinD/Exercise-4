package com.example.exercise4

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.support.constraint.Constraints.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AsyncTaskActivity : AppCompatActivity(), InterfaceAsyncTask {

    var textView: TextView? = null
    var task: MyAsyncTask? = null
    var currentPosition: Int = 0
    var textToSave: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task)

        textView = findViewById(R.id.text_async)

        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getInt("POS")
            if (task == null && currentPosition < 10) {
                task = MyAsyncTask(this)
                task?.execute(currentPosition)
            } else {
                textToSave = savedInstanceState.getString("TEXT")
                if (textToSave == getString(R.string.do_start)) {task = MyAsyncTask(this)}
                textView?.text = textToSave
            }
        }

        findViewById<Button>(R.id.create_async_btn).setOnClickListener(onCreateClick)
        findViewById<Button>(R.id.start_async_btn).setOnClickListener(onStartClick)
        findViewById<Button>(R.id.cancel_async_btn).setOnClickListener(onCancelClick)
    }

    // here buttons are binding
    private val onCreateClick = View.OnClickListener(){
        if (task == null) {
            task = MyAsyncTask(this)
            textView?.text = getString(R.string.do_start)
            textToSave = getString(R.string.do_start)
        } else Toast.makeText(applicationContext, getString(R.string.task_created), Toast.LENGTH_SHORT).show()
    }

    private val onStartClick = View.OnClickListener(){
        when(task?.status){
            AsyncTask.Status.RUNNING ->
                Toast.makeText(applicationContext, getString(R.string.task_started), Toast.LENGTH_SHORT).show()
            AsyncTask.Status.FINISHED -> {
                task = MyAsyncTask(this)
                task?.execute()
            }
            AsyncTask.Status.PENDING ->
                task?.execute()
            else ->
                Toast.makeText(applicationContext, getString(R.string.create_first), Toast.LENGTH_SHORT).show()
        }
        textToSave = ""
    }

    private val onCancelClick = View.OnClickListener(){
        task?.cancel(false)
        task = null
        currentPosition = 10
    }





    // here override method to pass our Text View
    override fun onPostExecute(result: String?) {
        textView?.text = result
        textToSave = result
        task = null
    }

    override fun onPreExecute() {
        Log.d(TAG, "start")
    }

    override fun onProgressUpdate(aInteger: Int?) {
        currentPosition = aInteger!!
        textView?.text = aInteger.toString()
    }

    override fun onCancel(result: String?) {
        textView?.text = result
        textToSave = result
        task = null
        currentPosition = 10
    }



    // here we need to save our position of counter
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (task?.status == AsyncTask.Status.PENDING)
            currentPosition = 10
        outState?.run {
            putInt("POS", currentPosition)
            putString("TEXT", textToSave)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("POS")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        task?.cancel(false)
    }
}
