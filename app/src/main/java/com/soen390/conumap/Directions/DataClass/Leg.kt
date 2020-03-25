package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Leg(
    @JsonProperty("distance")
    val distance: Distance?,
    @JsonProperty("duration")
    val duration: Duration?,
    @JsonProperty("end_address")
    val endAddress: String?,
    @JsonProperty("end_location")
    val endLocation: EndLocation?,
    @JsonProperty("start_address")
    val startAddress: String?,
    @JsonProperty("start_location")
    val startLocation: StartLocation?,
    @JsonProperty("steps")
    val steps: List<Step>?,
    @JsonProperty("traffic_speed_entry")
    val trafficSpeedEntry: List<Any>?,
    @JsonProperty("via_waypoint")
    val viaWaypoint: List<Any>?
)