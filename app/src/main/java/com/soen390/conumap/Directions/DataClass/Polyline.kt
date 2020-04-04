package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Polyline(
    @JsonProperty("points")
    val points: String?
)