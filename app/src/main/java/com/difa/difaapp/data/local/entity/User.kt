package com.difa.difaapp.data.local.entity

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val birtDate: String,
    val gender: String,
    val avatar: String? = "",
    val token: String
)
