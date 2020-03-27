package com.soen390.conumap.path

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PathViewModel: ViewModel()  {
    var transportationMode= MutableLiveData<String>()
    var alternativeOn= MutableLiveData<Boolean>()

    fun setTransportationMode(mode:String){
        transportationMode.value=mode
    }

}
