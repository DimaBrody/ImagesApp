package com.test.imagesapp.ui.fragments.popular

import android.os.Bundle
import androidx.activity.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.test.imagesapp.R
import com.test.imagesapp.databinding.FragmentPopularBinding
import com.test.imagesapp.functions.defaultFilter
import com.test.imagesapp.functions.photoReceiver
import com.test.imagesapp.functions.postponeForView
import com.test.imagesapp.ui.adapter.ImagesAdapter
import com.test.imagesapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularFragment : BaseFragment<FragmentPopularBinding>(R.layout.fragment_popular) {

    @Inject
    lateinit var adapter: ImagesAdapter

    @Inject
    lateinit var manager: LocalBroadcastManager

    private val receiver = photoReceiver {
        adapter.setNewFavorite(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager.registerReceiver(receiver, defaultFilter)
    }

    override fun setupBinding(binding: FragmentPopularBinding) {
        val viewModel: PopularViewModel by requireActivity().viewModels()
        binding.viewModel = viewModel

        binding.adapter = adapter.withFragmentType(fragmentType)

        if(requirePostpone)
            postponeForView(binding.popularRecyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.unregisterReceiver(receiver)
    }


}