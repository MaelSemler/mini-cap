package com.soen390.conumap.SVGConverter


import android.content.Context
import android.graphics.*
import android.renderscript.RenderScript
import com.soen390.conumap.IndoorNavigation.Node
import com.squareup.picasso.Transformation


class FloorPlanTransformation: Transformation {

    private lateinit var context:Context
    var svgCon: ConverterToFloorPlan = ConverterToFloorPlan
    lateinit var pathPaint: Paint
    lateinit var paint:Paint
    lateinit var canvas: Canvas
    private var mColor = 0


    init {
        context = svgCon.getContext()
        pathPaint = setupPathPaint()
        paint = setupMainPaint()

    }
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

    private fun setupMainPaint():Paint{
        paint = Paint()
        paint.setAntiAlias(true)
        paint.setColorFilter(PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP))
        return paint
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val bitmap = Bitmap.createBitmap(width, height,


        canvas = Canvas(bitmap)

        canvas.drawBitmap(source, 0.0F, 0.0F, paint)

        /////////////////EXAMPLE WITH FloatArray////////////////
        var exArray = floatArrayOf(800F,600F,2500F,600F, 3000F,1500F,3000F,3500F)
        drawLineWithArray(exArray)
        ///////////////////////////////////////////////////////

        //TODO: This should be the only method called here?
        drawPathIndoor()

        source.recycle()

        return bitmap
    }

    //TODO: Method that would run the algorithm, retrieve the points, then draw the path + other trimmings?
    fun drawPathIndoor(){
        retrievePathIndoor()
//        Loop thru the retrived data and draw the path
//        drawLineStartStop()
    }


    //TODO: This should be to link to Algorithm to retrieve the array of coordinates
    fun retrievePathIndoor(){

    }

    //This method would be the most straight forward since we can loop and call this
    fun drawLineStartStop(start:Node,stop:Node){
        canvas.drawLine(start.getX(), start.getY(), stop.getX(), stop.getY(), pathPaint)
    }


    //Second option: With this method you need to be careful
    // you need to repeat the last stopNode and copy it into the next startNode
    // We could probably override the method to make it work if we want to i guess
    // Just less straight forward to use
    //TODO: SEE EXAMPLE Up there
    fun drawLineWithArray(pathArray:FloatArray){
        canvas.drawLines(pathArray, pathPaint)
    }


}


