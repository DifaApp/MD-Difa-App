package com.difa.difaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("recommendations")
	val recommendations: List<RecommendationsItem>
)

data class RecommendationsItem(

	@field:SerializedName("url_video")
	val urlVideo: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url_image")
	val urlImage: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String?
)
