package com.test.imagesapp.data.repository

import com.test.imagesapp.data.database.dao.FavoritesDao
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.functions.parallelMap
import com.test.imagesapp.network.PhotosClient
import com.test.imagesapp.network.result.ResultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val photosClient: PhotosClient,
    private val favoritesDao: FavoritesDao
) {

    fun getFavoritesLiveData() = favoritesDao.getAllPhotosLiveData()

    suspend fun getFavoritesIds() = favoritesDao.getAllPhotosIds()

    suspend fun handleFavorite(photo: Photo) = favoritesDao.handlePhoto(photo)

    suspend fun fetchPhotos() = flow {
        val result = photosClient.fetchPhotos()

        if (result is ResultOf.Success) {
            val windowedData = result.data.windowed(20, 20, true)

            windowedData.forEach {
                emit(result.copy(data = it.parallelMap(::fetchSizeWithId)))
            }
        } else emit(result)
    }.flowOn(Dispatchers.IO)


    private suspend fun fetchSizeWithId(photo: Photo): Photo {
        val sizeResult = photosClient.fetchSizes(photo.id)

        if (sizeResult is ResultOf.Success)
            photo.sizes = sizeResult.data

        return photo
    }


}