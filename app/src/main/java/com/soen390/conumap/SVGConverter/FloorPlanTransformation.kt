package com.soen390.conumap.SVGConverter

import android.content.Context
import android.graphics.*
import android.renderscript.RenderScript
import androidx.core.content.ContextCompat
import com.soen390.conumap.IndoorNavigation.Node
import com.soen390.conumap.R
import com.squareup.picasso.Transformation

class FloorPlanTransformation: Transformation {

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
        canvas.drawBitmap(source, 0.0F, 0.0F, paint)

        var indoorPath = drawPathIndoor(source, 400, 367)
        drawLineWithArray(indoorPath)

        source.recycle()

        return bitmap
    }

    // Draws the path on the indoor floorplan.
    fun drawPathIndoor(source: Bitmap, numXNodes: Int, numYNodes: Int): FloatArray {
        var pathOfNodes = retrievePathIndoor()

        var pathToDraw = mutableListOf<Float>()

        // Adds coordinates to pathToDraw.
        for(i in 0 until pathOfNodes.size - 1) {
            var start = findNodeCoordinates(source, numXNodes, numYNodes, pathOfNodes[i].row, pathOfNodes[i].col)
            var end = findNodeCoordinates(source, numXNodes, numYNodes, pathOfNodes[i + 1].row, pathOfNodes[i + 1].col)

            // 4 floats to add for each node: startX, startY, endX, endY.
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
        return arrayOf(
            Node(31,291),
            Node(31,292),
            Node(32,292),
            Node(33,292),
            Node(34,292),
            Node(35,292),
            Node(35,293),
            Node(36,293),
            Node(36,294),
            Node(36,295),
            Node(36,296),
            Node(37,296),
            Node(38,296),
            Node(39,296),
            Node(39,297),
            Node(40,297),
            Node(41,297),
            Node(42,297),
            Node(43,297),
            Node(44,297),
            Node(45,297),
            Node(46,297),
            Node(47,297),
            Node(48,297),
            Node(49,297),
            Node(50,297),
            Node(51,297),
            Node(52,297),
            Node(53,297),
            Node(54,297),
            Node(55,297),
            Node(56,297),
            Node(57,297),
            Node(58,297),
            Node(59,297),
            Node(60,297),
            Node(61,297),
            Node(62,297),
            Node(63,297),
            Node(64,297),
            Node(65,297),
            Node(66,297),
            Node(67,297),
            Node(68,297),
            Node(69,297),
            Node(70,297),
            Node(71,297),
            Node(72,297),
            Node(73,297),
            Node(74,297),
            Node(75,297),
            Node(76,297),
            Node(77,297),
            Node(78,297),
            Node(79,297),
            Node(80,297),
            Node(81,297),
            Node(82,297),
            Node(83,297),
            Node(84,297),
            Node(85,297),
            Node(86,297),
            Node(87,297),
            Node(88,297),
            Node(89,297),
            Node(90,297),
            Node(91,297),
            Node(92,297),
            Node(93,297),
            Node(94,297),
            Node(95,297),
            Node(96,297),
            Node(97,297),
            Node(98,297),
            Node(99,297),
            Node(100,297),
            Node(101,297),
            Node(102,297),
            Node(103,297),
            Node(104,297),
            Node(105,297),
            Node(106,297),
            Node(107,297),
            Node(108,297),
            Node(109,297),
            Node(110,297),
            Node(111,297),
            Node(112,297),
            Node(113,297),
            Node(114,297),
            Node(115,297),
            Node(116,297),
            Node(117,297),
            Node(118,297),
            Node(119,297),
            Node(120,297),
            Node(121,297),
            Node(122,297),
            Node(123,297),
            Node(124,297),
            Node(125,297),
            Node(126,297),
            Node(127,297),
            Node(128,297),
            Node(129,297),
            Node(130,297),
            Node(131,297),
            Node(132,297),
            Node(133,297),
            Node(134,297),
            Node(135,297),
            Node(136,297),
            Node(137,297),
            Node(138,297),
            Node(139,297),
            Node(140,297),
            Node(141,297),
            Node(142,297),
            Node(143,297),
            Node(144,297),
            Node(145,297),
            Node(146,297),
            Node(147,297),
            Node(148,297),
            Node(149,297),
            Node(150,297),
            Node(151,297),
            Node(152,297),
            Node(153,297),
            Node(154,297),
            Node(155,297),
            Node(156,297),
            Node(157,297),
            Node(158,297),
            Node(159,297),
            Node(160,297),
            Node(161,297),
            Node(162,297),
            Node(163,297),
            Node(164,297),
            Node(165,297),
            Node(166,297),
            Node(167,297),
            Node(168,297),
            Node(169,297),
            Node(170,297),
            Node(171,297),
            Node(172,297),
            Node(173,297),
            Node(174,297),
            Node(175,297),
            Node(176,297),
            Node(177,297),
            Node(178,297),
            Node(179,297),
            Node(180,297),
            Node(181,297),
            Node(182,297),
            Node(183,297),
            Node(184,297),
            Node(184,298),
            Node(185,298),
            Node(186,298),
            Node(187,298),
            Node(188,298),
            Node(189,298),
            Node(189,299),
            Node(190,299),
            Node(191,299),
            Node(191,300),
            Node(192,300),
            Node(193,300),
            Node(194,300),
            Node(195,300),
            Node(196,300),
            Node(197,300),
            Node(198,300),
            Node(199,300),
            Node(200,300),
            Node(201,300),
            Node(202,300),
            Node(203,300),
            Node(204,300),
            Node(205,300),
            Node(206,300),
            Node(207,300),
            Node(208,300),
            Node(209,300),
            Node(210,300),
            Node(211,300),
            Node(212,300),
            Node(213,300),
            Node(214,300),
            Node(215,300),
            Node(216,300),
            Node(217,300),
            Node(218,300),
            Node(219,300),
            Node(220,300),
            Node(221,300),
            Node(222,300),
            Node(223,300),
            Node(224,300),
            Node(225,300),
            Node(226,300),
            Node(227,300),
            Node(228,300),
            Node(229,300),
            Node(230,300),
            Node(230,301),
            Node(231,301),
            Node(232,301),
            Node(233,301),
            Node(234,301),
            Node(235,301),
            Node(235,302),
            Node(236,302),
            Node(237,302),
            Node(238,302),
            Node(238,303),
            Node(238,304),
            Node(239,304),
            Node(240,304),
            Node(241,304),
            Node(242,304),
            Node(243,304),
            Node(244,304),
            Node(245,304),
            Node(246,304),
            Node(247,304),
            Node(248,304),
            Node(249,304),
            Node(250,304),
            Node(250,305),
            Node(251,305),
            Node(252,305),
            Node(253,305),
            Node(254,305),
            Node(255,305),
            Node(256,305),
            Node(256,306),
            Node(256,307),
            Node(257,307),
            Node(258,307),
            Node(259,307),
            Node(260,307),
            Node(261,307),
            Node(262,307),
            Node(263,307),
            Node(264,307),
            Node(265,307),
            Node(266,307),
            Node(267,307),
            Node(268,307),
            Node(269,307),
            Node(270,307),
            Node(271,307),
            Node(272,307),
            Node(273,307),
            Node(274,307),
            Node(275,307),
            Node(276,307),
            Node(277,307),
            Node(278,307),
            Node(279,307),
            Node(280,307),
            Node(281,307),
            Node(282,307),
            Node(283,307),
            Node(284,307),
            Node(285,307),
            Node(286,307),
            Node(287,307),
            Node(288,307),
            Node(289,307),
            Node(290,307),
            Node(291,307),
            Node(292,307),
            Node(293,307),
            Node(294,307),
            Node(295,307),
            Node(296,307),
            Node(297,307),
            Node(298,307),
            Node(299,307),
            Node(300,307),
            Node(301,307),
            Node(302,307),
            Node(303,307),
            Node(304,307),
            Node(305,307),
            Node(306,307),
            Node(307,307),
            Node(308,307),
            Node(309,307),
            Node(310,307),
            Node(311,307),
            Node(312,307),
            Node(313,307),
            Node(314,307),
            Node(315,307),
            Node(316,307),
            Node(317,307),
            Node(318,307),
            Node(319,307),
            Node(320,307),
            Node(321,307),
            Node(322,307),
            Node(323,307),
            Node(324,307),
            Node(325,307),
            Node(326,307),
            Node(327,307),
            Node(328,307),
            Node(329,307),
            Node(330,307),
            Node(331,307),
            Node(332,307),
            Node(333,307),
            Node(334,307),
            Node(335,307),
            Node(336,307),
            Node(337,307),
            Node(338,307),
            Node(339,307),
            Node(340,307),
            Node(341,307),
            Node(342,307),
            Node(343,307),
            Node(344,307),
            Node(345,307),
            Node(346,307),
            Node(347,307),
            Node(348,307),
            Node(349,307),
            Node(350,307),
            Node(351,307),
            Node(352,307),
            Node(353,307),
            Node(354,307)
        )
    }

    // Takes an array of Floats and draws line at desired position.
    // A line needs 4 floats: startX, startY, endX, endY.
    fun drawLineWithArray(pathArray:FloatArray){
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
