package com.soen390.conumap.ui.directions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soen390.conumap.path.Path

class AlternateViewModel : ViewModel() {

    // Return this when directions are determined
    val returnTotalDistanceText = Path._PathTotalDistanceText
    val returnTotalTimeText = Path._PathTotalTimeText
    val returnInfoPathText= Path._infoPathText

    val returnAlternateRouteId = Path._alternateRouteId
    val returnAlternateRouteIdMax = Path._alternateRouteIdMax
    val returnAlternateText = Path._alternateText
    //Get Path's Variables

    fun setTransportation(mode: String){
        Path.transportationMode=mode
    }

}
