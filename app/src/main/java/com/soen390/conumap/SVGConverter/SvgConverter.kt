package com.soen390.conumap.SVGConverter

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.graphics.toColor
import com.caverock.androidsvg.SVG
import com.soen390.conumap.R
import com.soen390.conumap.building.Floor

object SvgConverter:Application() {
//    private val activity = Activity()
//    val activity : Activity = Activity()

    private lateinit var context:Context
//
    var floorNode :Array<Array<Floor.FloorNode>> = arrayOf<Array<Floor.FloorNode>>()
//
//
//    fun setContext(ctx:Context){
//        context = ctx
//    }
//
//    fun getContext(): Context {
//        return context
//    }
    init {

    }

    @Override
    override fun onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    fun svgToBitMap(): Bitmap {

        val svgFile = SVG.getFromResource(context.resources, R.raw.hall8)
        lateinit var bitmapFile: Bitmap

        if (svgFile.getDocumentWidth() !== -1F) {

            // set your custom height and width for the svg
            svgFile.documentHeight = 600F
            svgFile.documentWidth = 600F

            // create a canvas to draw onto
            val bitmapFile = Bitmap.createBitmap(700,700, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmapFile)

            // canvas - white background
            canvas.drawARGB(0,255, 255, 255)

            // Render our document onto our canvas
            svgFile.renderToCanvas(canvas)

            // set the bitmap to imageView
//            imageV.background = BitmapDrawable(context.resources, bitmap)


        }
        return bitmapFile

    }

    fun convertSVGtoFloorPlan(bitmapFile: Bitmap): Floor.FloorPlan {

        for (x in 0 until bitmapFile.width) {
            for (y in 0 until bitmapFile.height) {

                //If it is a wall or a room then walkable set to false
                if ( bitmapFile.getPixel(x,y).toColor() == Color.rgb(218,54,54).toColor())
                    floorNode[x][y] = Floor.FloorNode(x,y,"#da3636", "id"+x+y, false, false)
                else//It is a hallway set walkable to true
                    floorNode[x][y] = Floor.FloorNode(x,y,"#da3636", "id"+x+y, true, false)

//                bitmapFile.setPixel(x, y, Color.rgb(255, 255, 255))
            }
        }
        var convertedfloorPlan = Floor.FloorPlan(floorNode)

        return convertedfloorPlan


    }
}