# FlickApp
Android Mobile application to search Flickr Images


Features:
1. Image search with keyword - Image can be searched by entering the keyword. The results are populated in 3 column grid fetched using flickr photo search api.
2. Endless scrolling - Endless scrolling is enabled to fetch all relevant results by repetitively hitting the api with consecutive page number once user reaches the bottom of search results.
3. Image caching - Image Caching has been implemented to save network and time while scrolling back and forth on the result page.


Screens:
1. ImageSearchActivity
2. SplashScreen


Tech stack:
1. MVVM Architecture with Android Architecture components
2. Kotlin
3. Mockito for unit testing
