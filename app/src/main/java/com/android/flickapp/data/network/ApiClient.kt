package com.android.flickapp.data.network

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by karthia on 20/09/18.
 */

/**
 * api client to make http requests in the worker thread and communicating the response back to
 * main thread using listener object @ResponseListener
 */
class ApiClient: AsyncTask<String, String, String> {

    private var responseListener: ResponseListener<String>
    private var urlStr: String

    constructor(responseListener: ResponseListener<String>, urlStr: String) : super() {
        this.responseListener = responseListener
        this.urlStr = urlStr
    }


    override fun doInBackground(vararg p0: String?): String? {

        try {

            val mUrl = URL(urlStr)
            val httpConnection = mUrl.openConnection() as HttpURLConnection
            httpConnection.requestMethod = "GET"
            httpConnection.setRequestProperty("Content-length", "0")
            httpConnection.useCaches = false
            httpConnection.allowUserInteraction = false
            httpConnection.connectTimeout = 10000
            httpConnection.readTimeout = 10000

            httpConnection.connect()

            val responseCode = httpConnection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {

                val allText = httpConnection.inputStream.bufferedReader().use(BufferedReader::readText)

                Log.d("response",allText)

                return allText
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        Log.d("response","error")

        return null

    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        responseListener.onResponse(result)
    }
}