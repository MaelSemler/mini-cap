package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Bounds(
    @JsonProperty("northeast")
    val northeast: Northeast?,
    @JsonProperty("southwest")
    val southwest: Southwest?
)