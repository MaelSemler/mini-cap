package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Route(
    @JsonProperty("bounds")
    val bounds: Bounds?,
    @JsonProperty("copyrights")
    val copyrights: String?,
    @JsonProperty("legs")
    val legs: List<Leg>?,
    @JsonProperty("overview_polyline")
    val overviewPolyline: OverviewPolyline?,
    @JsonProperty("summary")
    val summary: String?,
    @JsonProperty("warnings")
    val warnings: List<Any>?,
    @JsonProperty("waypoint_order")
    val waypointOrder: List<Any>?
)