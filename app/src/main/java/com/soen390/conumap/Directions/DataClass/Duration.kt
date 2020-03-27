package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Duration(
    @JsonProperty("text")
    val text: String?,
    @JsonProperty("value")
    val value: Int?
)