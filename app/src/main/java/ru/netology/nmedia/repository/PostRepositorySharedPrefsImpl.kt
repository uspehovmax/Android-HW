package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {

    private val json = Gson()
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val key = "posts"
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(key, null)?.let {
            posts = json.fromJson(it, type)
            data.value = posts
        }
    }

    override fun get(): LiveData<List<Post>> = data
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
        data.value = posts
        sync()
    }

    override fun share(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(shareCount = it.shareCount + 1)
        }
        data.value = posts
        sync()
    }

    override fun views(post: Post) {
        posts = posts.map {
            if (it.id != post.id) it else it.copy(viewsCount = it.viewsCount + 1)
        }
        data.value = posts
        sync()
        }

    override fun delete(post: Post) {
        posts = posts.filterNot { it.id == post.id }
        data.value = posts
        sync()
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun insert(post: Post) {
        posts = listOf(post.copy(id = ++nextId)) + posts
        data.value = posts
        sync()
    }

    private fun update(post: Post) {
        posts = posts.map { if (it.id == post.id) post else it }
        data.value = posts
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(key, json.toJson(posts))
            apply()
        }
    }

}