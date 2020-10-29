package com.test.imagesapp.ui.fragments.details

import android.view.View
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.test.imagesapp.R
import com.test.imagesapp.databinding.FragmentDetailsBinding
import com.test.imagesapp.functions.createPhotoIntent
import com.test.imagesapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {

    @Inject
    lateinit var localManager: LocalBroadcastManager

    private val navArgs: DetailsFragmentArgs by navArgs()

    override fun setupBinding(binding: FragmentDetailsBinding) {
        val viewModel: DetailsViewModel by viewModels()
        val photo = navArgs.photo

        val onClickListener = View.OnClickListener {
            viewModel.handlePhoto(photo)
            localManager.sendBroadcast(createPhotoIntent(photo))
        }
        
        val onBackClickListener = View.OnClickListener { 
            it.findNavController().popBackStack()
        }

        viewModel.setFavorite(photo.isSaved)
        binding.photo = photo
        binding.viewModel = viewModel
        binding.onClickListener = onClickListener
        binding.onBackClickListener = onBackClickListener
    }

}