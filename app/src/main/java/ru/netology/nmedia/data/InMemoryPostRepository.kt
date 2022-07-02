package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private var posts =
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

    private val dataPost = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = dataPost

    override fun likeById(post: Post) {
        posts = posts.map {
            if (it.id != post.id) {
                it
            } else if (it.id == post.id && !it.likedByMe) {
                it.copy(likes = it.likes + 1, likedByMe = !it.likedByMe)
            } else {
                it.copy(likes = it.likes - 1, likedByMe = !it.likedByMe)
            }
        }
        dataPost.value = posts
    }

    override fun share(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(shareCount = it.shareCount + 1)
        }
        dataPost.value = posts
    }

}
