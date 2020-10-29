package com.test.imagesapp.functions

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.test.imagesapp.data.model.Photo

const val PHOTO_ACTION = "default.action"

const val PHOTO_EXTRA = "default.photo.extra"

fun photoReceiver(onReceive: (String) -> Unit) = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val photo = intent?.getStringExtra(PHOTO_EXTRA)
        photo?.let(onReceive)
    }
}

val defaultFilter = IntentFilter().apply { addAction(PHOTO_ACTION) }

fun createPhotoIntent(photo: Photo): Intent {
    val intent = Intent(PHOTO_ACTION)
    intent.putExtra(PHOTO_EXTRA, photo.id)
    return intent
}