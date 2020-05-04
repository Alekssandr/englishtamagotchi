package com.szczecin.englishtamagotchi.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.szczecin.englishtamagotchi.R

@BindingAdapter("imgUrl")
fun ImageView.loadImage(url: String?) {
    url?.let {
        Glide.with(context)
            .load(url)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_launcher_background))
            .into(this)
    }
}