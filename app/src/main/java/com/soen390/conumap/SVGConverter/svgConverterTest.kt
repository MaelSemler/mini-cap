package com.soen390.conumap.SVGConverter

import com.caverock.androidsvg.SVG
import com.soen390.conumap.R

//class svgConverterTest {


    fun main(){
        val converter = SvgConverter

        val svgFile = SVG.getFromResource(converter.getContext().resources, R.raw.hall8)

        converter.svgToBitMap(svgFile)

    }
//}