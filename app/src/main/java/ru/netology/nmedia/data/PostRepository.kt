package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {

    fun get(): LiveData<List<Post>>
    fun like(post: Post)
    fun share(post: Post)
    fun delete(post: Post)
    fun save(post: Post)

    companion object {
        const val NEW_POST_ID = 0L
    }
}
