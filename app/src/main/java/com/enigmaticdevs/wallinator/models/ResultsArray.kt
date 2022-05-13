package com.enigmaticdevs.wallinator.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ResultsArray(@SerializedName("urls")  val url: PhotoUrl,
                        @SerializedName("user")  val user: User,
                        @SerializedName("id")  val id: String,
                        @SerializedName("alt_description")  val description: String,
                        @SerializedName("title")  val title: String,
                        @SerializedName("total_photos")  val totalPhotos : Int,
                        @SerializedName("cover_photo")  var coverPhoto: Photo)