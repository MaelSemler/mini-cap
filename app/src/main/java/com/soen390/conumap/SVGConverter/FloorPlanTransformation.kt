package com.soen390.conumap.SVGConverter

import android.accessibilityservice.GestureDescription
import android.content.Context
import android.graphics.*
import android.renderscript.RenderScript
import androidx.core.graphics.toColor
import com.squareup.picasso.Transformation


class FloorPlanTransformation: Transformation {

    private lateinit var context:Context
    var svgCon: SvgConverter = SvgConverter

    init {
        context = svgCon.getContext()
    }

    private var mColor = 0

    fun ColorFilterTransformation(color: Int) {
        mColor = color
    }

    var rs : RenderScript = RenderScript.create(context)

    override fun key(): String {
       return "Transformed floorPlan"
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        var canvas = Canvas(bitmap)
        var paint = Paint()
        var paint2 = Paint()

        paint2.setAntiAlias(true)
        paint2.setStyle(Paint.Style.STROKE)
        paint2.setStrokeWidth(20.0F)
        paint2.setColor(Color.BLUE)
        paint.setAntiAlias(true)
        this.ColorFilterTransformation(0)
        paint.setColorFilter(PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP))
        canvas.drawBitmap(source, 0.0F, 0.0F, paint)
        canvas.drawLine(900F,30F,3000F,800F,paint2)





//
//        for (x in 0 until source.width) {
//            for (y in 0 until source.height) {
//
//                //If it is a wall or a room then walkable set to false
//                if ( source.getPixel(x,y).toColor() == Color.rgb(218,54,54).toColor())
//
//    //                            floorNode[x][y] = Floor.FloorNode(x,y,"#da3636", "id"+x+y, false, false)
//                else//It is a hallway set walkable to true
//    //                            floorNode[x][y] = Floor.FloorNode(x,y,"#da3636", "id"+x+y, true, false)
//                    bitmap.setPixel(x, y, Color.rgb(255, 0, 0))
//            }
//        }


        source.recycle()

        return bitmap
    }
}


