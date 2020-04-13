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

//        /////////////////EXAMPLE WITH FloatArray////////////////
//        var exArray = floatArrayOf(
//            0F, 0F, 1993F, 0F,
//            1993F, 0F, 1993F, 100F,
//            1993f, 100f, 0f, 100f
//        )
//        drawLineWithArray(exArray)
//        ///////////////////////////////////////////////////////

        var indoorPath = drawPathIndoor(source)
        drawLineWithArray(indoorPath)

        //TODO: This should be the only method called here?
        // drawPathIndoor()

        source.recycle()

        return bitmap
    }

    //TODO: Method that would run the algorithm, retrieve the points, then draw the path + other trimmings?
    fun drawPathIndoor(source: Bitmap): FloatArray {
        var pathOfNodes = retrievePathIndoor()

        // 4 floats to add for each node: startX, startY, endX, endY.
        var pathToDraw = mutableListOf<Float>()

        for(i in 0..pathOfNodes.size) {
            if(i < pathOfNodes.size - 1) {
                // Not last node, we add the current node and the following node.
                var start = findNodeCoordinates(source, 3, 3, pathOfNodes[i].col, pathOfNodes[i].row)
                var end = findNodeCoordinates(source, 3, 3, pathOfNodes[i + 1].col, pathOfNodes[i + 1].row)

                pathToDraw.add(start[0])
                pathToDraw.add(start[1])
                pathToDraw.add(end[0])
                pathToDraw.add(end[1])
            } else {
//                // Last node, add the final node only.
//                var start = findNodeCoordinates(source, 3, 3, pathOfNodes[i].row, pathOfNodes[i].col)
//
//                pathToDraw.add(start[0])
//                pathToDraw.add(start[1])
            }
        }

        // Convert to floatArray so we can use it with drawLineWithArray and return it.
        return pathToDraw.toFloatArray()
    }


    //TODO: This should be to link to Algorithm to retrieve the array of coordinates
    fun retrievePathIndoor(): Array<Node> {
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
        var nodeWidth = source.width / numXNodes
        var nodeHeight = source.height / numYNodes

        // Find the center of the desired node.
        var nodeCenterX = nodeX * nodeWidth + nodeWidth / 2
        var nodeCenterY = nodeY * nodeHeight + nodeHeight / 2

        // Return an array with [xCoord, yCoord] of the node's center, which can be used to draw the path.
        var coordinates = floatArrayOf(nodeCenterX.toFloat(), nodeCenterY.toFloat())
        return coordinates
    }
}


