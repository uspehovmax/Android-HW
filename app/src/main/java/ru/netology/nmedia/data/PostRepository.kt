package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun get(): LiveData<List<Post>>
    fun likeById(post: Post)
    fun share(post: Post)

}