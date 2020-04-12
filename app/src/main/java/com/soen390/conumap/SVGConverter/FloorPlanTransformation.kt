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
    lateinit var pathPaint: Paint

    init {
        context = svgCon.getContext()
        pathPaint = setupPathPaint()


    }

    private var mColor = 0

    var rs : RenderScript = RenderScript.create(context)

    override fun key(): String {
       return "Transformed floorPlan"
    }

    private fun setupPathPaint(): Paint{
        pathPaint = Paint()
        pathPaint.isAntiAlias = true
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = 20.0F
        pathPaint.color = Color.BLUE
        return pathPaint
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        var canvas = Canvas(bitmap)
        var paint = Paint()
        paint.setAntiAlias(true)

        paint.setColorFilter(PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP))
        canvas.drawBitmap(source, 0.0F, 0.0F, paint)
        canvas.drawLine(900F,30F,3000F,800F,pathPaint)


        source.recycle()

        return bitmap
    }


}


