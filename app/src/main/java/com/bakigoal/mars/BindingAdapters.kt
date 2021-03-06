package com.bakigoal.mars

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bakigoal.mars.network.MarsProperty
import com.bakigoal.mars.overview.MarsApiStatus
import com.bakigoal.mars.overview.PhotoGridAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<MarsProperty>?) {
    val photoGridAdapter = recyclerView.adapter as PhotoGridAdapter
    photoGridAdapter.submitList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    url?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        imageView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
            crossfade(true)
            crossfade(500)
        }
    }
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, status: MarsApiStatus) {
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
