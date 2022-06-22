package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(dataPost.value) {
            "Data value should not be null"
        }

    override val dataPost = MutableLiveData(
        List(20) { index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет...",
                content = "Пост с номером $index",
                likes = index * 3,
                published = "27.05.2025",
                likedByMe = false,
                shareCount = index * 5,
                viewsCount = index * 7
            )
        }
    )

    override fun getAll(): LiveData<List<Post>> = dataPost

    override fun likeById(post: Post) {
        dataPost.value = posts.map {
            if (it.id != post.id) it
            else it.copy(likedByMe = !it.likedByMe)
        }
    }

    override fun share(shareCount: Int) {
        dataPost.value = posts.map {
            it.copy(shareCount = it.shareCount + 1)
        }
    }

    override fun views(viewsCount: Int) {
        dataPost.value = posts.map {
            it.copy(viewsCount = it.viewsCount + 1)
        }
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
/*
    override val dataPost = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет...",
                content = "Привет! Это новая Нетология...$index",
                likes = 555,
                published = "27.05.2025",
                likedByMe = false,
                shareCount = 1111999,
                viewsCount = 444669
            )
        }
    )
 */