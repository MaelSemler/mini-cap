package com.soen390.conumap.Directions.DataClass


import com.fasterxml.jackson.annotation.JsonProperty

data class Distance(
    @JsonProperty("text")
    val text: String?,
    @JsonProperty("value")
    val value: Int?
)