package com.android.flickapp.data.model

data class Photos(var page: Int,
                  var pages: Int,
                  var perPage: Int,
                  var total: String,
                  var photo: MutableList<Photo>)