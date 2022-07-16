package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()
    val data = repository.get()
    val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<PostContentActivity.EditPostResult?>()
    val playVideo = SingleLiveEvent<String>()

    fun onSaveButtonListener(content: String, newVideoUrl: String?) {
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Author of Post",
            content = content,
            published = "25 05 2022",
            video = newVideoUrl
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.like(post)

    override fun onRemoveClicked(post: Post) = repository.delete(post)

    override fun onShareClicked(post: Post) {
        repository.share(post)
        sharePostContent.value = post.content
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value =
            PostContentActivity.EditPostResult(post.content, post.video)
    }

    override fun onInsertClicked(post: Post) = repository.insert(post)

    fun onViews(post: Post) = repository.views(post)

    override fun onVideoPlayClicked(post: Post) {
        val url = requireNotNull(post.video)
        playVideo.value = url
    }

    fun onAddListener() {
        navigateToPostContentScreenEvent.call()
    }

}

/*

 */