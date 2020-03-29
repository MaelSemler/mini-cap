package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class OverviewPolyline(
    @JsonProperty("points")
    val points: String?
)