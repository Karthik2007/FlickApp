package com.android.flickapp.data.network

/**
 * Created by karthia on 20/09/18.
 */
interface ResponseListener<T> {

    fun onResponse(response: T?)

    fun onError(responseCode: Int)
}