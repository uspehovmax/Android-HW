package ru.netology.nmedia.dto

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val likes: Int = 0,
    val published: String,
    var likedByMe: Boolean = false,
    val shareCount: Int = 0,
    val viewsCount: Int = 0,
)