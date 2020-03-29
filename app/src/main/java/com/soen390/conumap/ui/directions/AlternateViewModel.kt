package com.soen390.conumap.ui.directions

import androidx.lifecycle.ViewModel
import com.soen390.conumap.path.Path

class AlternateViewModel : ViewModel() {
    // Return this when directions are determined
    val returnDirectionText = Path._PathDirectionText //This information is what is used to display on the Directions Fragment UI
    val returnAlternateText = Path._PathAlternateText //This information is what is used to display on the Directions Fragment UI
    val returnTotalDistanceText = Path._PathTotalDistanceText
    val returnTotalTimeText = Path._PathTotalTimeText
    val returnInfoPathText= Path._infoPathText
    //Get Path's Variables

    fun setTransportation(mode: String){
        Path.transportationMode=mode
    }
}