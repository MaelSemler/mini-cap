package com.soen390.conumap

import com.google.android.gms.maps.model.LatLng

abstract class Building {
    abstract var name: String
    abstract var info: String
    abstract var location: LatLng

    constructor(name: String, info: String, location: LatLng) {
        this.name = name
        this.info = info
        this.location = location
    }
}