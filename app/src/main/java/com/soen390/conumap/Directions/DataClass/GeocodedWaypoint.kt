package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class GeocodedWaypoint(
    @JsonProperty("geocoder_status")
    val geocoderStatus: String?,
    @JsonProperty("place_id")
    val placeId: String?,
    @JsonProperty("types")
    val types: List<String>?
)