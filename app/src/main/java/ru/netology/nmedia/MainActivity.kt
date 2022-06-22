package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(viewModel::likeById)

        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
    }
}

//        val adapterShare = PostsAdapter(viewModel::onShareClick)
//        binding.postsRecyclerView.adapter = adapterShare
//        viewModel.data.observe(this) { posts ->
//            adapterShare.submitList(posts)
//        }

//            // likesButton
//            binding.postsRecyclerView.setOnClickListener()
//            {
//                viewModel.onLikeButtonClick( )
//            }

//            // share
//            binding.share.setOnClickListener
//            {
//                viewModel.onShareClick()
//            }
//
//            // viewsNumber
//            viewModel.onCreateActivity()


//-----
/*       viewModel.data.observe(this) { posts ->
           posts.map { post ->
               binding.container.removeAllViews()
               CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
                   authorName.text = post.author
                   date.text = post.published
                   postText.text = post.content
                   likesButton.setImageResource(
                       if (post.likedByMe) R.drawable.ic_liked_24
                       else R.drawable.ic_baseline_favorite_24
                   )
                   likesButton.setOnClickListener {
                       viewModel.onLikeButtonClick(post)
                                      }.root
           }

*/

//                likesNumber.text = getEdit(post.likes)
//                shareNumber.text = getEdit(post.shareCount)
//                viewsNumber.text = getEdit(post.viewsCount)


//    private fun getEdit(numberLikes: Int): String {
//        var number2digits: Any
//        var text = ""
//        if (numberLikes in 1000..9999) {    // знак после ,
//            number2digits = (numberLikes / 100) / 10.0
//            text = "K"
//        } else if (numberLikes in 10_000..999_999) { // только целые числа >=10 до 999
//            number2digits = (numberLikes / 1000)
//            text = "K"
//        } else if (numberLikes > 999_999) {
//            number2digits = (numberLikes / 100_000) / 10.0
//            text = "M"
//        } else {
//            number2digits = numberLikes
//        }
//        return number2digits.toString() + text
//    }


//--------------------презентация --------------
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    val binding = ActivityMainBinding.inflate(layoutInflater)
//    setContentView(binding.root)
//
//    val viewModel: PostViewModel by viewModels()
//    viewModel.data.observe(this) { posts ->
//        posts.map { post ->
//            binding.container.removeAllViews()
//            CardPostBinding.inflate(layoutInflater, binding.container, true).apply {
//                authorName.text = post.author
//                date.text = post.published
//                postText.text = post.content
//                likesButton.setImageResource(
//                    if (post.likedByMe) R.drawable.ic_liked_24
//                    else R.drawable.ic_baseline_favorite_24
//                )
//                likesButton.setOnClickListener {
//                    viewModel.onLikeButtonClick(post)
//                }
//            }.root
//        }
///*
//            // likesButton
//            binding.likesButton.setOnClickListener
//            {
//                viewModel.onLikeButtonClick()
//            }
//
//            // share
//            binding.share.setOnClickListener
//            {
//                viewModel.onShareClick()
//            }
//
//            // viewsNumber
//            viewModel.onCreateActivity()
//*/
//
////                likesNumber.text = getEdit(post.likes)
////                shareNumber.text = getEdit(post.shareCount)
////                viewsNumber.text = getEdit(post.viewsCount)
//    }
//}

//----------- вебинар
//*/
/*
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { posts ->
            binding.render(posts)

        }

//        binding.root.setOnClickListener {
//            viewModel.onLikeButtonClick()
//        }

    }

        private fun PostBinding.render(post: Post) {
            authorName = post.author
            date.text = post.published
            postText.text = post.content
            likesButton.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24
                else R.drawable.ic_baseline_favorite_24
            )

    }

*/