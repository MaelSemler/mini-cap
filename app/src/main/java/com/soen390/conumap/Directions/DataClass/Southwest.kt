package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Southwest(
    @JsonProperty("lat")
    val lat: Double?,
    @JsonProperty("lng")
    val lng: Double?
)