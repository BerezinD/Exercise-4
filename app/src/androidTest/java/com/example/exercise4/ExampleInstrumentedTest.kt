package com.example.exercise4

import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import android.os.Looper


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
var counter = 0

    @Test
    @Throws(Exception::class)
    fun execute() {
        object : SimpleOfThread() {
            override fun onProgressUpdate() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            protected override fun onPreExecute() {
                assertTrue(isOnUiThread())
                assertEquals(counter++, 0)
            }

            protected override fun doInBackground(): Unit {
                assertFalse(isOnUiThread())
                assertEquals(counter++, 1)
                return Unit
            }

            protected override fun onPostExecute() {
                assertTrue(isOnUiThread())
                assertEquals(counter++, 2)
            }
        }.execute()
    }

    fun isOnUiThread(): Boolean {
        return Looper.myLooper() == Looper.getMainLooper()
    }
}
