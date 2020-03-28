package com.soen390.conumap.ui.search_bar

import androidx.lifecycle.ViewModel

class SearchBarViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var destination: String? = null

    fun getDestination(): String? {
        return destination
    }

    fun setDestination(d: String?) {
        destination = d
    }

    private var destinationAddress: String? = null

    fun setDestinationAddress(d: String?) {
        destinationAddress = d
    }
}

