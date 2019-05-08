package com.rjain90.engineeraiassignment.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("hits")
    val hits: List<Post>
)