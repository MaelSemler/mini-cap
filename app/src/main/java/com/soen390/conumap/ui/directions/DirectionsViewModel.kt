package com.soen390.conumap.ui.directions

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.soen390.conumap.Directions.directions
import com.soen390.conumap.map.Map

class DirectionsViewModel : ViewModel() {

    val directionText : LiveData<String>
        get() = directions.directionText
}
