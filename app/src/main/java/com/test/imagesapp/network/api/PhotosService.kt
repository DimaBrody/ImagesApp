package com.test.imagesapp.network.api

import com.test.imagesapp.data.model.Response
import com.test.imagesapp.data.model.sizes.ResponseSize
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosService {

    @GET("/services/rest/")
    suspend fun fetchPhotos(@Query("method") method: String = PHOTOS_LIST_METHOD) : Response

    @GET("/services/rest/")
    suspend fun fetchSizes(
        @Query("photo_id") id: String,
        @Query("method") method: String = PHOTO_SIZES_METHOD
    ): ResponseSize


    companion object {
        private const val PHOTOS_LIST_METHOD = "flickr.interestingness.getList"
        private const val PHOTO_SIZES_METHOD = "flickr.photos.getSizes"
    }
}