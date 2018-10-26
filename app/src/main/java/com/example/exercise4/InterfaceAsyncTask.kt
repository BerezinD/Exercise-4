package com.example.exercise4

interface InterfaceAsyncTask{
    fun onPostExecute(result: String?)
    fun onPreExecute()
    fun onProgressUpdate(aInteger: Int?)
    fun onCancel(result: String?)
}