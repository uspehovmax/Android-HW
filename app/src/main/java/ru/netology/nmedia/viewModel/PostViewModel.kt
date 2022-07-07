package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.get()
    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveButtonListener(content: String) {
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Author of Post",
            content = content,
            published = "25 05 2022"
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.like(post)

    override fun onRemoveClicked(post: Post) = repository.delete(post)

    override fun onShareClicked(post: Post) = repository.share(post)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }

    override fun onInsertClicked(post: Post) = repository.insert(post)

}

