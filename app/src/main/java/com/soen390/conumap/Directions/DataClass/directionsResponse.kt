package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class directionsResponse(
    @JsonProperty("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>?,
    @JsonProperty("routes")
    val routes: List<Route>?,
    @JsonProperty("status")
    val status: String?
)