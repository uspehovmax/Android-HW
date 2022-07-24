package ru.netology.nmedia.data

interface PostInteractionListener {
    fun onLikeClicked(post: Post)
    fun onRemoveClicked(post: Post)
    fun onShareClicked(post: Post)
    fun onEditClicked(post: Post)
    fun onInsertClicked(post: Post)
    fun onVideoPlayClicked(post: Post)
    fun onPostClicked(post: Post)

}
