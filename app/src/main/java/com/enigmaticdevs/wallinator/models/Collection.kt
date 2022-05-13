package com.enigmaticdevs.wallinator.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Collection(@SerializedName("id")val id : String,
                      @SerializedName("title")val title: String,
                      @SerializedName("description")val description: String,
                      @SerializedName("total_photos")val totalPhotos : Int,
                      @SerializedName("cover_photo")val coverPhoto: Photo ,
                      @SerializedName("user")val user: User)