package com.enigmaticdevs.wallinator.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Download(@SerializedName("url")  val url: String)