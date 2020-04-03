package com.soen390.conumap.SVGConverter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.core.graphics.toColor
import com.caverock.androidsvg.SVG
import com.soen390.conumap.R

object SvgConverter {
    private lateinit var context:Context

    fun setContext(ctx:Context){
        context = ctx
    }


    fun svgToBitMap(svgFile: SVG): Bitmap {

//        val svgFile = SVG.getFromResource(context.resources, R.drawable.hall8)
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

    fun convertSVGtoFloorPlan(bitmapFile: Bitmap){

        for (x in 0 until bitmapFile.width) {
            for (y in 0 until bitmapFile.height) {
                if ( bitmapFile.getPixel(x,y).toColor() == Color.rgb(255,255,255).toColor())


                    bitmapFile.setPixel(x, y, Color.rgb(255, 255, 255))
            }
        }

    }
}