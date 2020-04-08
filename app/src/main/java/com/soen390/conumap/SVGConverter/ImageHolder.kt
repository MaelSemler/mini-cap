package com.soen390.conumap.SVGConverter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.soen390.conumap.R
import com.squareup.picasso.Picasso

class ImageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val myImageView: ImageView = itemView.findViewById<ImageView>(R.id.myImageView)

    fun updateWithUrl(url: String) {
        Picasso.get().load(url).into(myImageView)
    }
}