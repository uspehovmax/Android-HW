package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.data.Post

interface PostRepository {

    fun get(): LiveData<List<Post>>
    fun like(post: Post)
    fun share(post: Post)
    fun views(post: Post)
    fun delete(post: Post)
    fun save(post: Post)
    fun insert(post: Post)

    companion object {
        const val NEW_POST_ID = 0L
    }
}
