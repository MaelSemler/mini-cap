package com.soen390.conumap.ui.directions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soen390.conumap.path.path

class AlternateViewModel : ViewModel() {

    val alternateText : LiveData<String>
        get() = path.alternateText
    val totalDistanceText: LiveData<String>
        get() = path.totalDistanceText
    val totalTimeText: LiveData<String>
        get() = path.totalTimeText
    val infoPathText : LiveData<String>
        get() = path.infoPathText

}
