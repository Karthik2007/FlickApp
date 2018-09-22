package com.android.flickapp.utilities

import android.content.Context
import com.android.flickapp.R
import com.android.flickapp.base.FlickApp
import com.android.flickapp.screen.imageSearch.ImageRecyclerAdapter.Companion.COLUMN_COUNT


class CommonUtil {


    companion object {

        /**
         * calculates the width of the image based on the screen size
         */
        fun calculateWidthOfImage(context: Context): Int {

            val displayMetrics = context.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels
            return (dpWidth / COLUMN_COUNT)
        }




        /**
         * constructs the flickr.photos.search url
         */
        fun constructUrl(context: Context): String
        {
            return String.format(context.getString(R.string.url),context.getString(R.string.base_url),
                    context.getString(R.string.method_name), context.getString(R.string.api_key),
                    context.resources.getInteger(R.integer.per_page_results),context.resources.getString(R.string.format))
        }
    }
}