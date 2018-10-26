package com.example.exercise4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ThreadsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_threads)

        findViewById<Button>(R.id.create_thread_btn).setOnClickListener(onCreateClick)
        findViewById<Button>(R.id.start_thread_btn).setOnClickListener(onStartClick)
        findViewById<Button>(R.id.cancel_thread_btn).setOnClickListener(onCancelClick)
    }

    private val onCreateClick = View.OnClickListener(){

    }

    private val onStartClick = View.OnClickListener(){

    }

    private val onCancelClick = View.OnClickListener(){

    }

}
