package com.soen390.conumap.SVGConverter

import android.content.Context
import android.graphics.*
import androidx.core.content.ContextCompat
import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.R
import com.squareup.picasso.Transformation

class FloorPlanTransformation(var indoorPath: Array<Node>): Transformation {

    private var context:Context
    private var mColor = 0
    lateinit var canvas: Canvas
    var svgCon: ConverterToFloorPlan = ConverterToFloorPlan
    var pathPaint: Paint
    var paint: Paint

    init {
        context = svgCon.getContext()
        pathPaint = setupPathPaint()
        paint = setupMainPaint()
    }

    override fun key(): String {
       return "Transformed floorPlan"
    }

    private fun setupPathPaint(): Paint {
        pathPaint = Paint()
        pathPaint.isAntiAlias = true
        pathPaint.style = Paint.Style.STROKE
        pathPaint.strokeWidth = 15f
        pathPaint.color = ContextCompat.getColor(context, R.color.indoorPath)
        return pathPaint
    }

    private fun setupMainPaint():Paint {
        paint = Paint()
        paint.setAntiAlias(true)
        paint.setColorFilter(PorterDuffColorFilter(mColor, PorterDuff.Mode.SRC_ATOP))
        return paint
    }

    // Transformation of indoor floor plan image which includes drawing an indoor path.
    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        canvas = Canvas(bitmap)
        canvas.drawBitmap(source, 0f, 0f, paint)

        var indoorPath = drawPathIndoor(source, 400, 367)
        drawLineWithArray(indoorPath)

        source.recycle()

        return bitmap
    }

    // Draws the path on the indoor floorplan.
    fun drawPathIndoor(source: Bitmap, numXNodes: Int, numYNodes: Int): FloatArray {
        var pathOfNodes = indoorPath

        var pathToDraw = mutableListOf<Float>()

        // Adds coordinates to pathToDraw.
        for(i in 0 until pathOfNodes.size - 1) {
            var start = findNodeCoordinates(source, numXNodes, numYNodes, pathOfNodes[i].col, pathOfNodes[i].row)
            var end = findNodeCoordinates(source, numXNodes, numYNodes, pathOfNodes[i + 1].col, pathOfNodes[i + 1].row)

            // 4 floats to add for each node: startX, startY, endX, endY.
            pathToDraw.add(start[0])
            pathToDraw.add(start[1])
            pathToDraw.add(end[0])
            pathToDraw.add(end[1])
        }

        // Convert to floatArray so we can use it with drawLineWithArray and return it.
        return pathToDraw.toFloatArray()
    }

    // Takes an array of Floats and draws line at desired position.
    // A line needs 4 floats: startX, startY, endX, endY.
    fun drawLineWithArray(pathArray: FloatArray){
        canvas.drawLines(pathArray, pathPaint)
    }

    // Function which returns the coordinates of a given node.
    fun findNodeCoordinates(source: Bitmap, numXNodes: Int, numYNodes: Int, nodeX: Int, nodeY: Int): FloatArray {
        // Determine the length and width of an individual node.
        val nodeWidth = source.width / numXNodes.toFloat()
        val nodeHeight = source.height / numYNodes.toFloat()

        // Find the center of the desired node.
        val nodeCenterX = (nodeX * nodeWidth) + (nodeWidth / 2)
        val nodeCenterY = (nodeY * nodeHeight) + (nodeHeight / 2)

        // Return an array with [xCoord, yCoord] of the node's center, which can be used to draw the path.
        return floatArrayOf(nodeCenterX, nodeCenterY)
    }
}
