package ru.netology.nmedia.data

data class Post (
    val id: Long,
    val author: String,
    val content: String,
    val likes: Int = 0,
    val published: String,
    val likedByMe: Boolean = false,
    val shareCount: Int = 0,
    val viewsCount: Int = 0,
    val video: String? = null,
)
