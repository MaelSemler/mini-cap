package com.soen390.conumap.path

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PathViewModel: ViewModel()  {
    var transportationMode= MutableLiveData<String>()
    var alternativeOn= MutableLiveData<Boolean>()

    fun setTransportationMode(mode:String){
        //Yomna's
        transportationMode.value=mode
//        Path.setTransportMode(mode)//TODO: I THINK THIS DOESNT CHANGE ANYTHING BUT TO REVIEW
    }
    fun getTransportationMode():String{
        return transportationMode.value.toString()
    }

}
