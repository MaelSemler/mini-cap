package com.soen390.conumap.ui.directions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class DirectionsViewModel : ViewModel() {

     var destinationChanged= MutableLiveData<Boolean>()
     var destinationName= MutableLiveData<String>()
     var destinationAddress= MutableLiveData<String>()
     var destinationLocation= MutableLiveData<LatLng>()

     var originName= MutableLiveData<String>()
     var originAddress= MutableLiveData<String>()
     var originLocation= MutableLiveData<LatLng>()
}
