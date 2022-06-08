package ru.netology.nmedia.data

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.MainActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private var post = Post(
        id = 0L,
        author = "Нетология. Университет...",
        content = "Привет! Это новая Нетология...",
        likes = 999,
        published = "27.05.2022",
        likedByMe = false,
        shareCount = 999999,
        viewsCount = 19999
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        post = post.copy(likedByMe = !post.likedByMe)
        post = post.copy(likes = likeCounter(post))
        data.value = post
    }

    override fun share() {
        post = post.copy(shareCount = post.shareCount + 1)
        data.value = post
    }

    override fun views() {
        post = post.copy(viewsCount = post.viewsCount + 1)
        data.value = post
    }

    private fun likeCounter(post: Post): Int {
        var likeCount = if (post.likedByMe) {
            post.likes + 1
        } else {
            post.likes - 1
        }
        return likeCount
    }

}
