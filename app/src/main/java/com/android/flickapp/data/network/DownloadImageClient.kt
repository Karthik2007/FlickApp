package com.android.flickapp.data.network

import android.graphics.Bitmap
import android.os.AsyncTask
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.android.flickapp.data.cache.MemoryCache
import java.lang.ref.WeakReference


/**
 * Created by karthia on 20/09/18.
 *
 *
 * ImageClient to asynchronously download the images to feed the target  view
 */
class DownloadImageClient: AsyncTask<String, String, Bitmap> {

    private var viewReference: WeakReference<ImageView>
    private var imageUrlStr: String
    private var memoryCache: MemoryCache
    private var position: Int

    constructor(imageUrlStr: String, imageView: ImageView, memoryCache: MemoryCache, pos: Int) : super() {
        this.imageUrlStr = imageUrlStr
        this.memoryCache = memoryCache
        viewReference = WeakReference(imageView)
        this.position = pos

    }


    override fun doInBackground(vararg p0: String?): Bitmap? {

        // check the target view is recycled or not, if recycled already, cancel the request
        if(viewReference.get() != null) {
            var imageView = viewReference.get() as ImageView

            if(imageView.tag as Int != position) {
                return null
            }

        }

        var bmp: Bitmap? = null
        try {
            val inputStream = java.net.URL(imageUrlStr).openStream()
            bmp = BitmapFactory.decodeStream(inputStream)

            inputStream.close()
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        return bmp
    }


    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)


        if(result != null)
        {
            // add the bitmap to our  memory cache
            memoryCache.put(imageUrlStr,result)

            // check the target view is not recycled and set the bitmap to image
            if(viewReference.get() != null) {
                var imageView = viewReference.get() as ImageView

                if(imageView.tag as Int == position) {
                    imageView.setImageBitmap(result)
                }

            }
        }


    }

}