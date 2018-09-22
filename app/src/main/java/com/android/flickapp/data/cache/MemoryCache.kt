package com.android.flickapp.data.cache

import android.graphics.Bitmap
import android.util.LruCache

/**
 * Created by karthia on 21/09/18.
 */
class MemoryCache(var maxsize: Int): LruCache<String, Bitmap?>(maxsize) {

    override fun sizeOf(key: String, value: Bitmap?): Int {
        return value?.byteCount ?: 0
    }

    override fun entryRemoved(evicted: Boolean, key: String, oldValue: Bitmap?, newValue: Bitmap?) {
        //oldValue?.recycle()
    }


}