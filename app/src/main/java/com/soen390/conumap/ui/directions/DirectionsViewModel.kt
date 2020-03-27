package com.soen390.conumap.ui.directions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soen390.conumap.path.Path

class DirectionsViewModel : ViewModel() {

    val directionText = Path.directionText  //This information is what is used to display on the Directions Fragment UI
    val totalDistanceText = Path.totalDistanceText
    val totalTimeText = Path.totalTimeText
    val infoPathText= Path.infoPathText

}
