package com.soen390.conumap.SVGConverter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.R

class ImageAdapter(var imageUrl: Int, var indoorPath: Array<Node>): RecyclerView.Adapter<ImageHolder>() {
    val imageUrls: Array<Int> = arrayOf(imageUrl)

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        var imageUrl = imageUrls[position]
        holder?.updateIndoorImage(imageUrl, indoorPath)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        var imageItem = LayoutInflater.from(parent?.context).inflate(R.layout.image_item, parent, false)
        return ImageHolder(imageItem)
    }
}
