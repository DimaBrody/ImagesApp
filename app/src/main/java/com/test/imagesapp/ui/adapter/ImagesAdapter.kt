package com.test.imagesapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.imagesapp.R
import com.test.imagesapp.data.model.Photo
import com.test.imagesapp.databinding.ItemImageBinding
import com.test.imagesapp.ui.base.BaseFragment
import com.test.imagesapp.ui.fragments.favorites.FavoritesFragmentDirections
import com.test.imagesapp.ui.fragments.popular.PopularFragmentDirections
import javax.inject.Inject

class ImagesAdapter @Inject constructor(
    private val picasso: Picasso,
    private var items: List<Photo> = listOf()
) : RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {

    private val TAG = this::class.java.simpleName

    var fragmentType: Int = -1

    fun setItems(items: List<Photo>) {
        val previousCount = itemCount

        val itemsCount = items.size - previousCount

        when {
            itemsCount > 0 -> {
                this.items = items
                notifyItemRangeInserted(previousCount, itemsCount)
            }
            itemsCount < 0 -> {
                this.items = items
                notifyDataSetChanged()
            }
        }
    }

    fun setNewFavorite(photoId: String) {
        items.find { it.id == photoId }?.let {
            it.isSaved = !it.isSaved
        }
    }


    fun withFragmentType(fragmentType: Int): ImagesAdapter {
        this.fragmentType = fragmentType
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder =
        ImagesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_image,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        val item = items[position]
        item.photoFragmentType = fragmentType

        val clickListener = View.OnClickListener {
            val action = when (fragmentType) {
                BaseFragment.FragmentType.FAVORITES -> FavoritesFragmentDirections
                    .actionFavoritesFragmentToDetailsFragment(item)
                BaseFragment.FragmentType.POPULAR -> PopularFragmentDirections
                    .actionPopularFragmentToDetailsFragment(item)
                else -> return@OnClickListener
            }

            item.lowQualityPlaceholder = (it as? ImageView)?.drawable?.toBitmap()

            it.findNavController().navigate(action)
        }

        holder.bind(items[position], clickListener)
    }

    override fun getItemCount(): Int = items.size

    inner class ImagesViewHolder(private val binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo, onClickListener: View.OnClickListener) {
            binding.photo = photo
            binding.picasso = picasso
            binding.clickListener = onClickListener
        }
    }
}