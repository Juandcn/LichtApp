package co.edu.unab.mgads.jdcn.applicht.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter

@BindingAdapter("image")
fun loadImage(imageView: ImageView, url:String){
    Glide.with(imageView.context).load(url).into(imageView)
}