package com.android.flickapp.screen.imageSearch

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import com.android.flickapp.R
import com.android.flickapp.data.model.Photo
import com.android.flickapp.utilities.CommonUtil
import com.android.flickapp.utilities.ImageLoader

class ImageRecyclerAdapter(private var context: Context, private var photoList: MutableList<Photo>): RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>() {


    private val imageLoader: ImageLoader = ImageLoader(context)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {

        var imageView = ImageView(context)

        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        val size = CommonUtil.calculateWidthOfImage(context.applicationContext)
        val leftRightMargin = 8 * 3
        val layoutParams = GridLayout.LayoutParams(ViewGroup.LayoutParams(size - leftRightMargin, size))
        layoutParams.bottomMargin = 8
        imageView.layoutParams = layoutParams


        return ImageViewHolder(imageView)

    }

    override fun getItemCount(): Int {

        return photoList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, pos: Int) {

        var imageView = holder.itemView as ImageView

        val imgUrl = "https://farm"+ photoList[pos].farm +".static.flickr.com/" + photoList[pos].server + "/" +
                photoList[pos].id + "_" + photoList[pos].secret + ".jpg"



        imageView.tag = pos
        imageView.setBackgroundColor(context.resources.getColor(R.color.imageBackgroundGray))
        imageView.setImageBitmap(null)


        imageLoader.displayImage(imgUrl,imageView,pos)
    }


    fun setData(newPhotoList: MutableList<Photo>)
    {
        this.photoList = newPhotoList
        notifyDataSetChanged()
    }

    fun addData(newPhotoList: MutableList<Photo>)
    {
        this.photoList.addAll(newPhotoList)
    }

    class ImageViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)


    companion object {
        const val COLUMN_COUNT: Int = 3
    }
}