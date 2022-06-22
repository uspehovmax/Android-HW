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
import ru.netology.nmedia.viewModel.PostViewModel

class PostsAdapter(
    private val onLikeListener: (Post) -> Unit,
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding, onLikeListener)
    }

    override fun onBindViewHolder(holder: PostsAdapter.PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(
        private val binding: CardPostBinding,
        private val onLikeViewHolderListener: (Post) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            authorName.text = post.author
            date.text = post.published
            postText.text = post.content
            likesNumber.text = post.likes.toString()
            shareNumber.text = post.shareCount.toString()
            viewsNumber.text = post.viewsCount.toString()

            //  перекрашивание сердечка происходит после прокрутки - обновления
            likesButton.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24
                else R.drawable.ic_baseline_favorite_24
            )
            likesButton.setOnClickListener {
                onLikeViewHolderListener(post)
            }

            // после обновления происходит сброс значений
            share.setOnClickListener {
                val postNew = post.copy(shareCount = post.shareCount + 1)
                shareNumber.text = postNew.shareCount.toString()
            }


        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
//            likesButton.setOnClickListener {
//                val postNew: Post
//                if (!post.likedByMe) {
//                    postNew = post.copy(likedByMe = !post.likedByMe, likes = post.likes + 1)
//                   // R.drawable.ic_liked_24
//                } else {
//                    postNew = post.copy(likedByMe = !post.likedByMe, likes = post.likes - 1)
//                   // R.drawable.ic_baseline_favorite_24
//                }
//                likesNumber.text = postNew.likes.toString()
//                onLikeViewHolderListener(post)
//            }

//            {
//                val postNew = post.copy(likedByMe = !post.likedByMe)
//
//                {
//                    likesNumber.text = (postNew.likes + 1).toString()
//                    likesButton.setImageResource(R.drawable.ic_liked_24)
//                }
//                else {
//                    likesNumber.text = (postNew.likes - 1).toString()
//                    likesButton.setImageResource(R.drawable.ic_baseline_favorite_24)
//                }
//            }

//            @DrawableRes
//            fun getLikeIconResId(likedByMe: Boolean) =
//                if (likedByMe) R.drawable.ic_liked_24
//                else R.drawable.ic_baseline_favorite_24

//        private fun likesButton.setImageResource(
//        if (post.likedByMe) R.drawable.ic_liked_24
//        else R.drawable.ic_baseline_favorite_24
//        )

//        likesButton.setOnClickListener {
//
//
//        }
//        share.setOnClickListener {
//            //onShareClick(post)
//        }


