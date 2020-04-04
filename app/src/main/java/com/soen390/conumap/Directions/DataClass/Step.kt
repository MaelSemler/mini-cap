package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Step(
    @JsonProperty("distance")
    val distance: DistanceX?,
    @JsonProperty("duration")
    val duration: DurationX?,
    @JsonProperty("end_location")
    val endLocation: EndLocationX?,
    @JsonProperty("html_instructions")
    val htmlInstructions: String?,
    @JsonProperty("maneuver")
    val maneuver: String?,
    @JsonProperty("polyline")
    val polyline: Polyline?,
    @JsonProperty("start_location")
    val startLocation: StartLocationX?,
    @JsonProperty("travel_mode")
    val travelMode: String?
)