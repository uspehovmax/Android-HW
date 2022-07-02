package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.getEdit
import ru.netology.nmedia.viewModel.PostViewModel

class PostsAdapter(
    private val onLikeListener: (Post) -> Unit,
    private val onShareListener: (Post) -> Unit,
) : ListAdapter<Post, PostViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeViewHolderListener: (Post) -> Unit,
    private val onShareViewHolderListener: (Post) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) = with(binding) {
        authorName.text = post.author
        date.text = post.published
        postText.text = post.content

        likesNumber.text = getEdit(post.likes)
        shareNumber.text = getEdit(post.shareCount)
        viewsNumber.text = getEdit(post.viewsCount)

        likesButton.setImageResource(
            if (post.likedByMe) R.drawable.ic_liked_24
            else R.drawable.ic_baseline_favorite_24
        )
        likesButton.setOnClickListener {
            onLikeViewHolderListener(post)
        }
        share.setOnClickListener {
            onShareViewHolderListener(post)
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