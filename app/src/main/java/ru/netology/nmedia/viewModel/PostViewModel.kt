package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.get()
    fun like(post: Post) = repository.likeById(post)
    fun share(post: Post) = repository.share(post)

}
