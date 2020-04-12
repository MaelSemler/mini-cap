package com.soen390.conumap.SVGConverter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ImageHolder

class ImageAdapter(): RecyclerView.Adapter<ImageHolder>() {
//    val imageUrls: Array<String>

    init {
//        imageUrls = arrayOf()
    }

    override fun getItemCount(): Int {
        return 1
//        return imageUrls.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
//        var imageUrl = imageUrls[position]
        holder?.updateWithR()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        var imageItem = LayoutInflater.from(parent?.context).inflate(R.layout.image_item, parent, false)
        return ImageHolder(imageItem)
    }
}