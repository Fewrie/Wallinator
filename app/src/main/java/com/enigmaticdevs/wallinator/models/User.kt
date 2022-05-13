package com.enigmaticdevs.wallinator.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(@SerializedName("id")  val id: String,
                @SerializedName("username")  val username: String,
                @SerializedName("name") val name : String,
                @SerializedName("profile_image")  val profileImage : ProfileImage,
                @SerializedName("total_photos")  val TotalPhotos: String,
                @SerializedName("total_collections")  val collection: String)