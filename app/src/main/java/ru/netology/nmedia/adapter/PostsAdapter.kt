package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostInteractionListener
import ru.netology.nmedia.data.counter
import ru.netology.nmedia.databinding.CardPostBinding

class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val listener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) = with(binding) {
        authorName.text = post.author
        date.text = post.published
        postText.text = post.content
        likesButton.isChecked = post.likedByMe
        videoGroup.isVisible = post.video != null

        views.text = counter(post.viewsCount)
        shareNumber.text = counter(post.shareCount)
        likesButton.text = counter(post.likes)

        likesButton.setOnClickListener {
            listener.onLikeClicked(post)
        }

        shareNumber.setOnClickListener {
            listener.onShareClicked(post)
        }

        videoPreview.setOnClickListener {
            listener.onVideoPlayClicked(post)
        }

        postText.setOnClickListener {
            listener.onPostClicked(post)
        }

        options.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.option_post)
                setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        R.id.insert -> {
                            listener.onInsertClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }
}

private object DiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}

