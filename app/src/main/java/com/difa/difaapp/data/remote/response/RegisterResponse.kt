package com.difa.difaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("datas")
	val datas: Datas,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class Datas(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String
)
