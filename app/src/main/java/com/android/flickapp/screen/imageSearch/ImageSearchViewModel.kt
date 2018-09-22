package com.android.flickapp.screen.imageSearch

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import com.android.flickapp.R
import com.android.flickapp.base.FlickApp
import com.android.flickapp.data.model.ImageSearchResponse
import com.android.flickapp.data.network.ApiClient
import com.android.flickapp.data.network.ResponseListener
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

/**
 * Created by karthia on 20/09/18.
 */
class ImageSearchViewModel(application: Application) : AndroidViewModel(application) {


    var imageSearchRes = MutableLiveData<ImageSearchResponse>()


    /**
     * method to hit the search photos api and feed the response to the observable
     */
    fun getPhotos(endpointUrl: String,searchText: String, page: Int) {

        val url = "$endpointUrl&text=$searchText+&page=$page"

        ApiClient(object : ResponseListener<String> {

            override fun onResponse(response: String?) {

                try {

                    imageSearchRes.postValue(parseResponse(response))

                }catch (e: Exception)
                {
                    imageSearchRes.postValue(null)

                    e.printStackTrace()
                }

            }

            override fun onError(responseCode: Int) {

                imageSearchRes.postValue(null)
            }

        }, url).execute()
    }



    /**
     * loads more images once the scroll reaches the bottom of the grid
     */
    fun loadMoreImages(lastVisibleItemPosition: Int, pagePerResults: Int,
                       lastLoadedPage: Int, nextPageAvailable: Boolean, curSearchText: String): Boolean {

        val noOfItemsAtLastTwoRows = 6
        // checks whether scroll has reached the bottom of loaded contents
        if (lastLoadedPage * pagePerResults - lastVisibleItemPosition < noOfItemsAtLastTwoRows
                && nextPageAvailable) {

            return true
        }

        return false
    }
    /**
     * method to parse the photos.search response
     */
    fun parseResponse(response: String?): ImageSearchResponse? {

        if(response != null && response.isEmpty())
            return null

        val gson = Gson()
        val type = object : TypeToken<ImageSearchResponse>() {}.type

        return gson.fromJson(response, type)
    }


}