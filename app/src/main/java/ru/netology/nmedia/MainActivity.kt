package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel by viewModels<PostViewModel>()
        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddListener()
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideo.observe(this) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }

        val postContentActivityLauncher =
            registerForActivityResult(PostContentActivity.ResultContract) { postContentAndVideo ->
                postContentAndVideo ?: return@registerForActivityResult
                viewModel.onSaveButtonListener(postContentAndVideo.newContent, postContentAndVideo.newVideoUrl)
            }

        viewModel.navigateToPostContentScreenEvent.observe(this) { postContentAndVideo ->
            postContentActivityLauncher.launch(postContentAndVideo)
        }

    }

}

fun getEdit(numberLikes: Int): String {
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


/*

 */