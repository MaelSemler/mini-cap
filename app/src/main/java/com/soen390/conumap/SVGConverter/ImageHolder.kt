package com.soen390.conumap.SVGConverter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class ImageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val myImageView: ImageView = itemView.findViewById(R.id.myImageView)

    fun updateIndoorImage(resource: Int, indoorPath: Array<Node>) {
        Picasso.get()
            .load(resource)
            .transform(FloorPlanTransformation(indoorPath))
            .into(myImageView)
    }
}