package com.test.imagesapp.ui.fragments.details

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.picasso.Picasso
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.data.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val dataRepository: DataRepository,
    val picasso: Picasso
) : ViewModel() {

    val isFavorite = ObservableBoolean()

    fun setFavorite(isSaved: Boolean) {
        isFavorite.set(isSaved)
    }

    fun handlePhoto(photo: Photo) = viewModelScope.launch {
        val isSaved = dataRepository.handleFavorite(photo)
        isFavorite.set(isSaved)
    }

}