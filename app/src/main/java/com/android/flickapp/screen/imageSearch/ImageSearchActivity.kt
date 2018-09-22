package com.android.flickapp.screen.imageSearch

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.android.flickapp.R
import kotlinx.android.synthetic.main.activity_image_search.*
import com.android.flickapp.data.model.Photo
import com.android.flickapp.data.model.Photos
import com.android.flickapp.screen.imageSearch.ImageRecyclerAdapter.Companion.COLUMN_COUNT
import com.android.flickapp.utilities.CommonUtil
import org.jetbrains.anko.toast


/**
 * Created by karthia on 20/09/18.
 */
class ImageSearchActivity : AppCompatActivity() {

    lateinit var viewModel: ImageSearchViewModel
    private var photoList: MutableList<Photo> = mutableListOf()
    private lateinit var adapter: ImageRecyclerAdapter

    private var lastLoadedPage: Int = 0 // recently loaded page number set to 0 initially
    private lateinit var curSearchText: String // recently searched text
    private var nextPageAvailable = true
    private var isSearchLoading = false
    private var perPageResult: Int = 50
    private lateinit var photoSearchUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_search)

        init()

        viewModel = ViewModelProviders.of(this).get(ImageSearchViewModel::class.java)
        initViews()
        initBinding()


        // first time image search is run with default search text - configured in values/api_config.xml
        searchImage(resources.getString(R.string.default_search_text))

    }

    private fun init() {
        photoSearchUrl = CommonUtil.constructUrl(this.applicationContext)
        perPageResult = resources.getInteger(R.integer.per_page_results)
    }


    /**
     * initializes views and view related listeners
     */
    private fun initViews() {

        //setting up the Image recycler view
        adapter = ImageRecyclerAdapter(this, photoList)
        images_grid_view.layoutManager = GridLayoutManager(this, COLUMN_COUNT) as RecyclerView.LayoutManager?
        images_grid_view.adapter = adapter


        search_view.isSubmitButtonEnabled = true
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                searchImage(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                if (newText.isEmpty()) {
                    curSearchText = resources.getString(R.string.default_search_text)
                }

                return false
            }
        })

        images_grid_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition = (recyclerView?.layoutManager as GridLayoutManager).findLastVisibleItemPosition()


                var result = viewModel.loadMoreImages(lastVisibleItemPosition, perPageResult,
                        lastLoadedPage, nextPageAvailable, curSearchText)

                if(result && !isSearchLoading) {

                    viewModel.getPhotos(photoSearchUrl,curSearchText, lastLoadedPage + 1)
                    showProgress(true)
                    isSearchLoading = true
                }

            }
        })

    }

    /**
     * searches image based on the query entered
     */
    private fun searchImage(query: String) {

        if (query.isNotEmpty()) // omit empty query search
        {
            curSearchText = query
            showProgress(true)
            lastLoadedPage = 0
            viewModel.getPhotos(photoSearchUrl, query, lastLoadedPage + 1) // requesting photos for query text
            isSearchLoading = true
        }
    }

    /**
     * method to bind the view model to the activity class
     * all the observers are initialized here
     */
    private fun initBinding() {

        //observer for searched photos response
        viewModel.imageSearchRes.observe(this, Observer {


            if (it != null) {

                if (it.photos != null) {

                    showResults(it.photos)

                } else {
                    toast(getString(R.string.invalid_api_key))
                }

            } else {
                toast(getString(R.string.generic_error_message))
            }
            isSearchLoading = false
            showProgress(false)
        })

    }

    /**
     * populates the result on the recycler view
     */
    private fun showResults(photos: Photos) {
        lastLoadedPage = photos.page
        nextPageAvailable = photos.pages > lastLoadedPage

        if (lastLoadedPage == 1) {
            // replace the list if it is first time
            photoList = photos.photo
            adapter.setData(photoList)

        } else {
            // append to the existing list
            adapter.addData(photos.photo)
            adapter.notifyItemRangeInserted(photos.page * 50, 50)
        }


        if (photos.pages < 1) {
            toast(getString(R.string.no_results))
        }
    }

    /**
     * progress visibility switch method
     */
    private fun showProgress(show: Boolean) {
        progress_bar?.visibility = if (show) View.VISIBLE else View.GONE
    }
}
