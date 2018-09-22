package com.android.flickapp.imageSearch

import android.app.Application
import android.content.Context
import com.android.flickapp.screen.imageSearch.ImageSearchViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ImageSearchViewModelTest {

    @Mock
    lateinit var application: Application


    lateinit var imageSearchViewModel: ImageSearchViewModel

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        imageSearchViewModel = Mockito.spy(ImageSearchViewModel(application))
    }


    @Test
    fun parseResponse_withValidResponse() {

        var response = imageSearchViewModel.parseResponse("{ \"photos\": { \"page\": 1, \"pages\": \"40873\", \"perpage\": 5, \"total\": \"204362\", \n" +
                "    \"photo\": [\n" +
                "      { \"id\": \"43931306635\", \"owner\": \"11963487@N08\", \"secret\": \"0f72b523dc\", \"server\": \"1862\", \"farm\": 2, \"title\": \"Moving some cat pics in.\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 },\n" +
                "      { \"id\": \"43931303665\", \"owner\": \"11963487@N08\", \"secret\": \"55edd55e10\", \"server\": \"1915\", \"farm\": 2, \"title\": \"Moving some cat pics in.\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 },\n" +
                "      { \"id\": \"43030804440\", \"owner\": \"11963487@N08\", \"secret\": \"e19c558293\", \"server\": \"1919\", \"farm\": 2, \"title\": \"Moving some cat pics in.\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 },\n" +
                "      { \"id\": \"30969855068\", \"owner\": \"11963487@N08\", \"secret\": \"a3b8303e00\", \"server\": \"1939\", \"farm\": 2, \"title\": \"Moving some cat pics in.\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 },\n" +
                "      { \"id\": \"29905437457\", \"owner\": \"11963487@N08\", \"secret\": \"f251c6ed1e\", \"server\": \"1948\", \"farm\": 2, \"title\": \"Moving some cat pics in.\", \"ispublic\": 1, \"isfriend\": 0, \"isfamily\": 0 }\n" +
                "    ] }, \"stat\": \"ok\" }")

        assertNotNull(response)

    }


    @Test
    fun parseResponse_withInValidResponse() {

        var response = imageSearchViewModel.parseResponse("")

        assertNull(response)

    }

    @Test
    fun parseResponse_withNullResponse() {

        var response = imageSearchViewModel.parseResponse("")

        assertNull(response)

    }


    @Test
    fun loadMoreImages_scrolledToMiddle() {

        var endpointUrl = ""
        imageSearchViewModel.loadMoreImages(25, 50,1,true,"kittens")

        Mockito.verify(imageSearchViewModel,Mockito.never()).getPhotos(endpointUrl,"",1)
    }


    @Test
    fun loadMoreImages_scrolledToBottom() {

        var endpointUrl = ""
        var result = imageSearchViewModel.loadMoreImages(45, 50,1,true,"kittens")

        assertTrue(result)
    }


    @Test
    fun loadMoreImages_nextPageNotAvailable() {

        var endpointUrl = ""
        imageSearchViewModel.loadMoreImages(45, 50,1,false,"kittens")

        Mockito.verify(imageSearchViewModel,Mockito.never()).getPhotos(endpointUrl,"",1)
    }



}