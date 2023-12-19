package com.difa.difaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem?>? = null
)

data class RecommendationsItem(

	@field:SerializedName("url_video")
	val urlVideo: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url_image")
	val urlImage: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
