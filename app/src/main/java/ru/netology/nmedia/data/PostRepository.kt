package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    val dataPost: LiveData<List<Post>>
    fun getAll(): LiveData<List<Post>>
    fun likeById(post: Post)
    fun share(shareCount: Int)
    fun views(viewsCount: Int)

}