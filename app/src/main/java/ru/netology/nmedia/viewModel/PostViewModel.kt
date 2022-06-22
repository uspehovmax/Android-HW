package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.getAll()  // из презентации
    //val data by repository::dataPost

    fun likeById(post: Post) = repository.likeById(post)  // из презентации

    //fun onLikeButtonClickRepository(post: Post) = repository.likeById(post.id)
    fun onShareClick(post: Post) = repository.share(post.shareCount)
    fun onCreateActivity(post: Post) = repository.views(post.viewsCount)

}
