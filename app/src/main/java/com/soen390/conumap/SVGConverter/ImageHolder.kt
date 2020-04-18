package com.soen390.conumap.SVGConverter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.soen390.conumap.R
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import jp.wasabeef.picasso.transformations.GrayscaleTransformation

class ImageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    private val myImageView: ImageView = itemView.findViewById(R.id.myImageView)

    fun updateWithR() {
        //TODO: Hardcoded floorplan for now
        //Note: load(DOES NOT TAKE IN AS ARGUMENT SVG)
        Picasso.get()
            .load(R.drawable.h8floorplan)
            .transform(FloorPlanTransformation())
            .into(myImageView)
    }

    fun updateIndoorImage(resource: Int) {
        Picasso.get()
            .load(resource)
            .transform(FloorPlanTransformation())
            .into(myImageView)
    }
}