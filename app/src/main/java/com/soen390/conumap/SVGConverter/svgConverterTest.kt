package com.soen390.conumap.SVGConverter

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGImageView
import com.soen390.conumap.R
import com.soen390.conumap.helper.ContextPasser

//class svgConverterTest {


    fun main(){
        val converter = SvgConverter
//        val context = converter.getContext()

//        val fileSVG = SVG.getFromResource(, R.raw.hall8)

        val bitmapPlan = converter.svgToBitMap()

    }
//}