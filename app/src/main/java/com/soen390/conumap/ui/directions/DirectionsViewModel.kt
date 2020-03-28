package com.soen390.conumap.ui.directions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soen390.conumap.path.Path

class DirectionsViewModel : ViewModel() {

    val directionText : LiveData<String>
        get() = Path.directionText
    val totalDistanceText: LiveData<String>
        get() = Path.totalDistanceText
    val totalTimeText: LiveData<String>
        get() = Path.totalTimeText
    val infoPathText : LiveData<String>
        get() = Path.infoPathText


}
