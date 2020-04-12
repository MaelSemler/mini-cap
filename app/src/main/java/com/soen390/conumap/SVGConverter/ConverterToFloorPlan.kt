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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

object ConverterToFloorPlan{
//    private val activity = Activity()
//    val activity : Activity = Activity()

    private lateinit var context:Context

    var floorNode :Array<Array<Floor.FloorNode>> = arrayOf<Array<Floor.FloorNode>>()

    fun setContext(ctx:Context){
        context = ctx
    }

    fun getContext(): Context {
        return context
    }
    init {

    }

    fun svgToBitMap(): Bitmap? {
        val svgFile = SVG.getFromResource(context.resources, R.raw.hall8)
        var bitmapFile: Bitmap? = null



            if (svgFile.getDocumentWidth() !== -1F) {

                // set your custom height and width for the svg
                svgFile.documentHeight = 600F
                svgFile.documentWidth = 600F

                // create a canvas to draw onto
                bitmapFile = Bitmap.createBitmap(svgFile.documentWidth.toInt()+1,svgFile.documentHeight.toInt()+1, Bitmap.Config.ARGB_8888)
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

    suspend fun convertToPlan(bitmapFile: Bitmap?): Floor.FloorPlan {
        lateinit var convertedfloorPlan: Floor.FloorPlan

        var numRow = bitmapFile?.height
        var numCol = bitmapFile?.width

        for (i in 0..numCol!!){
            var row = arrayOf<Floor.FloorNode>()
            for(j in 0..numRow!!) {
                row += Floor.FloorNode(-1,-1, "#000000", "", false, false)
            }
            floorNode += row
        }


        coroutineScope {
            launch(Dispatchers.IO) {
                if (bitmapFile != null) {
                    for (x in 0 until bitmapFile.width) {
                        for (y in 0 until bitmapFile.height) {

                            //If it is a wall or a room then walkable set to false
                            if ( bitmapFile.getPixel(x,y).toColor() == Color.rgb(218,54,54).toColor())
                                floorNode[x][y] = Floor.FloorNode(x,y,"#da3636", "id"+x+y, false, false)

                            else//It is a hallway set walkable to true

                                continue
                                floorNode[x][y] = Floor.FloorNode(x,y,"#da3636", "id"+x+y, true, false)

            //                bitmapFile.setPixel(x, y, Color.rgb(255, 255, 255))
                        }
                    }
                }
            }
            convertedfloorPlan = Floor.FloorPlan(floorNode)
        }

        return convertedfloorPlan

    }
}