package com.test.imagesapp.data.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import androidx.versionedparcelable.VersionedParcelize
import com.test.imagesapp.data.database.converters.ListConverter
import com.test.imagesapp.data.model.sizes.Size
import com.test.imagesapp.ui.base.BaseFragment
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "photo_table")
@Parcelize
data class Photo(
    @PrimaryKey
    val id: String,
    val title: String,
    var sizes: List<Size> = listOf(),
    var isSaved: Boolean = false
) : Parcelable {
    @Ignore var photoFragmentType = BaseFragment.FragmentType.POPULAR
    @Ignore var lowQualityPlaceholder: Bitmap? = null
}