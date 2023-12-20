package com.difa.difaapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailProfileResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User
)

data class User(

	@field:SerializedName("profilePicture")
	val profilePicture: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("birthdate")
	val birthdate: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String
)
