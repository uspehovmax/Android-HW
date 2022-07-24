package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.activity.EditPostResult
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostInteractionListener
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel(application: Application) : AndroidViewModel(application),
    PostInteractionListener {

    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data = repository.get()
    val currentPost = MutableLiveData<Post?>(null)
    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<EditPostResult?>()
    val playVideo = SingleLiveEvent<String>()
    val openPostEvent = SingleLiveEvent<Long>()

    fun onSaveButtonListener(content: String, newVideoUrl: String?) {
        val post = currentPost.value?.copy(
            content = content,
            video = newVideoUrl
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
            EditPostResult(post.content, post.video)
    }

    override fun onInsertClicked(post: Post) = repository.insert(post)

    override fun onVideoPlayClicked(post: Post) {
        val url = requireNotNull(post.video)
        playVideo.value = url
    }

    override fun onPostClicked(post: Post) {
        repository.views(post)
        openPostEvent.value = post.id
    }
}
