package com.android.flickapp.utilities

import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import com.android.flickapp.data.cache.MemoryCache
import android.app.ActivityManager
import android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND
import android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE
import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.ImageView
import com.android.flickapp.data.network.DownloadImageClient


/**
 * Created by karthia on 21/09/18.
 */
class ImageLoader(private var context: Context): ComponentCallbacks2 {


    private  var memoryCache: MemoryCache

    init {

        val am = context.getSystemService(
                Context.ACTIVITY_SERVICE) as ActivityManager
        val maxKb = am.memoryClass * 1024 * 1024
        val limitKb = maxKb / 5    // 1/8th of total ram

        memoryCache = MemoryCache(limitKb)
    }




    fun displayImage(imageUrl: String, imageView: ImageView, pos: Int)
    {
        val bitMap = memoryCache.get(imageUrl)

        if(bitMap != null)
        {
            imageView.setImageBitmap(bitMap)
        }else
        {
            DownloadImageClient(imageUrl,imageView, memoryCache, pos).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
        }
    }

    override fun onConfigurationChanged(p0: Configuration?) {

    }

    override fun onLowMemory() {

    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onTrimMemory(level: Int) {

        if (level >= TRIM_MEMORY_MODERATE) {
            memoryCache.evictAll()
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            memoryCache.trimToSize(memoryCache.size() / 2)
        }
    }

}