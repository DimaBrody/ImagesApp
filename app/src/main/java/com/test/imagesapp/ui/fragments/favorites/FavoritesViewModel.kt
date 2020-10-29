package com.test.imagesapp.ui.fragments.favorites

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val isLoading = ObservableBoolean()

    var photosLiveData: LiveData<List<Photo>> = MutableLiveData()

    init {
        isLoading.set(true)
        viewModelScope.launch { getPhotos() }
    }

    private suspend fun getPhotos() {
        photosLiveData = liveData(Dispatchers.IO) {
            emitSource(repository.getFavoritesLiveData())
            isLoading.set(false)
        }
    }

}