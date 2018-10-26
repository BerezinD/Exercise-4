package com.example.exercise4

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.threads_activity_btn).setOnClickListener {
            val intent = Intent(this, ThreadsActivity::class.java).apply {
                //here to put some extras
            }
            startActivity(intent)
        }
        findViewById<Button>(R.id.asynctask_activity_btn).setOnClickListener {
            val intent = Intent(this, AsyncTaskActivity::class.java).apply {
                //here to put some extras
            }
            startActivity(intent)
        }
    }
}
