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
        val width = source.width // 1993
        val height = source.height // 1829

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//        val bitmap = Bitmap.createBitmap(width, height,


        canvas = Canvas(bitmap)

        canvas.drawBitmap(source, 0.0F, 0.0F, paint)

        var indoorPath = drawPathIndoor(source, 3, 3)
        drawLineWithArray(indoorPath)

        source.recycle()

        return bitmap
    }

    // Draws the path on the indoor floorplan.
    fun drawPathIndoor(source: Bitmap, numXNodes: Int, numYNodes: Int): FloatArray {
        var pathOfNodes = retrievePathIndoor()

        // 4 floats to add for each node: startX, startY, endX, endY.
        var pathToDraw = mutableListOf<Float>()

        // Adds coordinates to pathToDraw.
        for(i in 0 until pathOfNodes.size - 1) {
            var start = findNodeCoordinates(source, numXNodes, numYNodes, pathOfNodes[i].col, pathOfNodes[i].row)
            var end = findNodeCoordinates(source, numXNodes, numYNodes, pathOfNodes[i + 1].col, pathOfNodes[i + 1].row)

            pathToDraw.add(start[0])
            pathToDraw.add(start[1])
            pathToDraw.add(end[0])
            pathToDraw.add(end[1])
        }

        // Convert to floatArray so we can use it with drawLineWithArray and return it.
        return pathToDraw.toFloatArray()
    }

    //TODO: This should be to link to Algorithm to retrieve the array of coordinates
    fun retrievePathIndoor(): Array<Node> {
        // Temporary to test, should actually get path from pathfinding algorithm.
        return arrayOf(Node(0, 0), Node(0, 1), Node(0, 2),
            Node(1, 2), Node(1, 1), Node(1, 0),
            Node(2, 0), Node(2, 1), Node(2, 2)
        )
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

    // Function which returns the coordinates of a given node.
    fun findNodeCoordinates(source: Bitmap, numXNodes: Int, numYNodes: Int, nodeX: Int, nodeY: Int): FloatArray {
        // Determine the length and width of an individual node.
        val nodeWidth = source.width / numXNodes
        val nodeHeight = source.height / numYNodes

        // Find the center of the desired node.
        val nodeCenterX = nodeX * nodeWidth + nodeWidth / 2
        val nodeCenterY = nodeY * nodeHeight + nodeHeight / 2

        // Return an array with [xCoord, yCoord] of the node's center, which can be used to draw the path.
        return floatArrayOf(nodeCenterX.toFloat(), nodeCenterY.toFloat())
    }
}


