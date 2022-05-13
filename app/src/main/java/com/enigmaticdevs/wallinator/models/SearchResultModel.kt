package com.enigmaticdevs.wallinator.models

import com.google.gson.annotations.SerializedName
import java.util.*

class SearchResultModel(@SerializedName("results")  val results: ArrayList<ResultsArray?>,
                        @SerializedName("total")  val total : Int,
                        @SerializedName("total_pages")  val totalPages: Int)