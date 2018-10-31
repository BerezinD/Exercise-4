package com.example.exercise4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class ThreadsActivity : AppCompatActivity() {
    companion object {
        const val TAG = "TAG"
    }

    private var mNewSimpleOfThread: SimpleOfThread? = null

    var textView: TextView? = null

    var i = 10

    private var textToSave = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        textView = findViewById(R.id.text_thread)
        findViewById<Button>(R.id.create_thread_btn).setOnClickListener(onCreateClick)
        findViewById<Button>(R.id.start_thread_btn).setOnClickListener(onStartClick)
        findViewById<Button>(R.id.cancel_thread_btn).setOnClickListener(onCancelClick)

        if (savedInstanceState != null){
            i = savedInstanceState.getInt("I", 10)
            textToSave = savedInstanceState.getString("S", "")
            Log.d(TAG, "$i")
            when(textToSave){
                getString(R.string.create_first) -> { i = 0; mNewSimpleOfThread = null }
                getString(R.string.cancel) -> mNewSimpleOfThread?.cancel()
                getString(R.string.do_start) -> { i = 0; beginMyTask() }
                getString(R.string.done) -> mNewSimpleOfThread = null
            }
            if (i in 1..9){
                beginMyTask()
                mNewSimpleOfThread?.execute()
            } else {
                textView?.text = textToSave
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        mNewSimpleOfThread?.cancel()
        mNewSimpleOfThread = null
        if (i == 0) i = 10
        outState?.putInt("I", i)
        outState?.putString("S", textView?.text.toString())
        Log.d(TAG, "$i")
        super.onSaveInstanceState(outState)
    }

    private val onCreateClick = View.OnClickListener {
        if (mNewSimpleOfThread != null) {
            Toast.makeText(this, "task already created!", Toast.LENGTH_SHORT).show()
        } else {
            textView?.text = getString(R.string.do_start)
            i = 0
            beginMyTask()
        }
    }

    private val onStartClick = View.OnClickListener{
        if (mNewSimpleOfThread != null) {
            mNewSimpleOfThread?.execute()
        } else textView?.text = getString(R.string.create_first)
    }

    private val onCancelClick = View.OnClickListener{
        if (mNewSimpleOfThread != null) {
            mNewSimpleOfThread?.cancel()
            mNewSimpleOfThread = null
            i = 0
        } else {
            textView?.text = ""
        }
    }

    private fun beginMyTask() {
        mNewSimpleOfThread = object : SimpleOfThread() {

            override fun onPreExecute() {
                Log.d(TAG, "on PRE execute")
            }

            override fun doInBackground() {
                Log.d(TAG, "do in background")
                while (i < 10) {
                    if (mInterrupted) { i = 0; return }
                    try {
                        Thread.sleep(500)
                    } catch (e: Exception) {
                        Log.d(TAG, "catch $e")
                    }
                    i++
                    Log.d(TAG, "$i")
                    publishProgress()
                }
            }

            override fun onPostExecute() {
                Log.d(TAG, "on post execute")
                if (mInterrupted) textView?.text = getString(R.string.cancel) else
                textView?.text = getString(R.string.done)
            }

            override fun onProgressUpdate() {
                textView?.text = i.toString()
            }
        }
    }
}