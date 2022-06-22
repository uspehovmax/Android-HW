package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                authorName.text = post.author
                date.text = post.published
                postText.text = post.content
                likesButton.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24
                    else R.drawable.ic_baseline_favorite_24
                )
                likesNumber.text = getEdit(post.likes)
                shareNumber.text = getEdit(post.shareCount)
                viewsNumber.text = getEdit(post.viewsCount)
            }
        }

        // likesButton
        binding.likesButton.setOnClickListener {
            viewModel.onLikeButtonClick()
        }

        // share
        binding.share.setOnClickListener {
            viewModel.onShareClick()
        }

        // viewsNumber
        viewModel.onCreateActivity()
    }

    private fun getEdit(numberLikes: Int): String {
        var number2digits: Any
        var text = ""
        if (numberLikes in 1000..9999) {    // знак после ,
            number2digits = (numberLikes / 100) / 10.0
            text = "K"
        } else if (numberLikes in 10_000..999_999) { // только целые числа >=10 до 999
            number2digits = (numberLikes / 1000)
            text = "K"
        } else if (numberLikes > 999_999) {
            number2digits = (numberLikes / 100_000) / 10.0
            text = "M"
        } else {
            number2digits = numberLikes
        }
        return number2digits.toString() + text
    }

}
