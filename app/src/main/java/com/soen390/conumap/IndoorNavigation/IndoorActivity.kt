package com.soen390.conumap.IndoorNavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.soen390.conumap.R
import com.soen390.conumap.SVGConverter.ImageAdapter
import com.soen390.conumap.SVGConverter.SvgConverter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_indoor.*

class IndoorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_indoor)


        imageRecycler.layoutManager = LinearLayoutManager(this)
        imageRecycler.adapter = ImageAdapter()

        val svgCon = SvgConverter
//        val floorP =  svgCon.convertSVGtoFloorPlan(svgCon.svgToBitMap())




//        Picasso
//            .get() // give it the context
//            .load("https://raw.githubusercontent.com/MaelSemler/mini-cap/DEV-26-Automate-Conversion-Map/app/src/main/res/drawable/h9floorplan.png") // load the image
//            .into(myImageView) // select the ImageView to load it into

    }
}
