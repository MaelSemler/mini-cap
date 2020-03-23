package com.soen390.conumap.path

import androidx.lifecycle.ViewModel

class PathViewModel: ViewModel()  {
    private  var transportationMode=""
    private var alternativeOn=false

    fun getTransportationMode(): String {
        return transportationMode
    }

    fun setTransportationMode(mode:String){
        transportationMode=mode
    }

    fun getAlternative(): Boolean {
        return alternativeOn
    }

    fun setAlternative(option:Boolean){
        alternativeOn=option
    }

}