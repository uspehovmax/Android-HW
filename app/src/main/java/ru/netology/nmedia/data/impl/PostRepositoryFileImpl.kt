package ru.netology.nmedia.data.impl

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class PostRepositoryFileImpl(private val context: Context) : PostRepository {
    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private val filename = "json.posts"
    private var nextId = 0L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)
    private val idPref = context.getSharedPreferences("id", Context.MODE_PRIVATE)
    private val key = "id_key"
    init {
        nextId = idPref.getLong(key, 0)
        val file = context.filesDir.resolve(filename)
        try {
            if (file.exists()) {
                context.openFileInput(filename).bufferedReader().use {
                    posts = gson.fromJson(it, type)
                    data.value = posts
                }
            } else sync()
        } catch (ex: Exception) {
            Toast.makeText(context, "Sorry, posts can't be loaded", Toast.LENGTH_SHORT).show()
            sync()
        }
    }

    private fun sync() {
        context.openFileOutput(filename, Context.MODE_PRIVATE).bufferedWriter().use {
            it.write(gson.toJson(posts))
        }
    }

    private fun saveId() {
        idPref.edit().apply {
            putLong(key, nextId)
            apply()
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
                it.copy(likes = it.likes  - 1, likedByMe = !it.likedByMe)
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

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun insert(post: Post) {
        posts = listOf(post.copy(id = ++nextId)) + posts
        data.value = posts
        sync()
        saveId()
    }

    override fun delete(post: Post) {
        posts = posts.filterNot { it.id == post.id }
        data.value = posts
        sync()
    }

    private fun update(post: Post) {
        posts = posts.map { if (it.id == post.id) post else it }
        data.value = posts
        sync()
    }

    override fun views(post: Post) {
        //
    }

}