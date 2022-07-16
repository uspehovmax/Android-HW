package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.data.impl.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.hideKeyboard
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

        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Text can't be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val content = text.toString()
                viewModel.onSaveButtonListener(content)
                clearFocus()
                hideKeyboard()
                binding.editGroup.visibility = View.GONE
            }
        }

        binding.editCancelButton.setOnClickListener {
            viewModel.currentPost.value = null
            binding.editGroup.visibility = View.GONE
        }

        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    requestFocus()
                    binding.editGroup.visibility = View.VISIBLE
                    binding.editTextBottom.text = content
                }
            }
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
