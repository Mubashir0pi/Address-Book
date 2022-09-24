package com.assesment.addressbook.bindingAdapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.assesment.addressbook.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("profilePictureUrl")
fun bindProfilePicture(imageView: ImageView, profilePictureUrl: String?) {

    profilePictureUrl?.let {
        val profilePictureUri = profilePictureUrl.toUri()

        Glide.with(imageView.context)
            .load(profilePictureUri)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_loading_profile_picture)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)
    }
}
