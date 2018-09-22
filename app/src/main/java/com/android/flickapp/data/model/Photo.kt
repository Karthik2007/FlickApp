package com.android.flickapp.data.model

/**
 *
 */
data class Photo (var id: String,
                  var owner: String,
                  var secret: String,
                  var server: String,
                  var farm: Int,
                  var title: String,
                  var ispublic: Int,
                  var isfamily: Int,
                  var isfriend: Int)