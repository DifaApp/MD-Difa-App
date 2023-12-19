package com.difa.difaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)
