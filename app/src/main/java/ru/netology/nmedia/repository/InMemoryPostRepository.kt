package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.data.Post

class InMemoryPostRepository : PostRepository { //PostRepositoryFileImpl(application)

    companion object {
        const val POST_COUNTER = 10
    }

    private var nextId = POST_COUNTER.toLong()

    private var posts =
        List(POST_COUNTER) { index ->
            Post(
                id = index + 1L,
                author = "Нетология. Университет...",
                content = "Пост с номером $index",
                likes = index * 50,
                published = "27.05.2025",
                likedByMe = false,
                shareCount = index * 5,
                viewsCount = index * 10,
                video = "https://www.youtube.com/watch?v=2AWcWODemB8"
            )
        }

    private val dataPost = MutableLiveData(posts)

    override fun get(): LiveData<List<Post>> = dataPost

    override fun like(post: Post) {
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

    override fun views(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(viewsCount = it.viewsCount + 1)
        }
        dataPost.value = posts
    }

    override fun delete(post: Post) {
        posts = posts.filterNot { it.id == post.id }
        dataPost.value = posts
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun insert(post: Post) {
        posts = listOf(post.copy(id = ++nextId)) + posts
        dataPost.value = posts
    }

    private fun update(post: Post) {
        posts = posts.map { if (it.id == post.id) post else it }
        dataPost.value = posts
    }

}


/*

 */