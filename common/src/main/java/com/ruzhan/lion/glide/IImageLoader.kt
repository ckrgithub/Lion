package com.ruzhan.lion.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.RequestListener

/**
 * Created by ruzhan123 on 2018/7/4.
 */
interface IImageLoader {

    fun load(imageView: ImageView, url: String)

    fun load(imageView: ImageView, resId: Int)

    fun load(imageView: ImageView, url: String, placeholder: Drawable)

    fun load(imageView: ImageView, resId: Int, placeholder: Drawable)

    fun loadNoCrossFade(imageView: ImageView, url: String)

    fun loadNoCrossFade(imageView: ImageView, resId: Int)

    fun loadNoCrossFade(imageView: ImageView, url: String, placeholder: Drawable)

    fun loadNoCrossFade(imageView: ImageView, resId: Int, placeholder: Drawable)

    fun loadCenterCrop(imageView: ImageView, url: String)

    fun loadCenterCrop(imageView: ImageView, url: String, listener: RequestListener<Drawable>)

    fun loadCenterCrop(imageView: ImageView, resId: Int)
}