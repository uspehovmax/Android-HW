package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val originalPost = Post(
        id = 0L,
        author = "Нетология. Университет...",
        content = "Привет! Это новая Нетология...",
        likes = 999,
        published = "27.05.2022",
        likedByMe = false,
        shareCount = 999999,
        viewsCount = 999
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Копируем пост с возможностью редактирования для проверки отображения разных вариантов
        var editPost = originalPost

        binding.render(editPost)

        // likesButton
        binding.likesButton?.setOnClickListener {
            println("binding.likesButton.setOnClickListener")
            editPost.likedByMe = !editPost.likedByMe
            binding.likesButton.setImageResource(getLikeIconResId(editPost.likedByMe))
            editPost = editPost.copy(likes = likeCounter(editPost))    // обновляем пост
            binding.likesNumber.text = getEdit(editPost.likes)        //
        }

        // editLikeNumber - для ввода значений - проверка
        binding.editLikeNumber.setOnClickListener {
            var likesEdit =
                binding.editLikeNumber.text.toString().toInt()  // считываем введеное число
            editPost = editPost.copy(likes = likesEdit)    // обновляем пост
            binding.likesNumber.text = getEdit(likesEdit)
        }

        // shareNumber
        binding.share?.setOnClickListener {
            var counter = editPost.shareCount + 1
            editPost = editPost.copy(shareCount = shareCounter(editPost))    // обновляем пост
            binding.shareNumber.text = getEdit(editPost.copy(shareCount = counter).shareCount)
        }

        // editShareNumber - для ввода значений - проверка
        binding.editShareNumber.setOnClickListener {
            var share = binding.editShareNumber.text.toString().toInt()
            editPost = editPost.copy(shareCount = share)    // обновляем пост
            binding.shareNumber.text = getEdit(share)
        }

        // viewsNumber
        binding.viewsNumber.text =
            getEdit(editPost.copy(viewsCount = editPost.viewsCount + 1).viewsCount)
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
        } else if (numberLikes > 999_999) {  // знак после ,
            number2digits = (numberLikes / 100_000) / 10.0
            text = "M"
        } else {
            number2digits = numberLikes
        }
        return number2digits.toString() + text
    }

    private fun ActivityMainBinding.render(editPost: Post) {
        authorName.text = editPost.author
        postText.text = editPost.content
        date.text = editPost.published
        likesNumber.text = editPost.likes.toString()
        shareNumber.text = editPost.shareCount.toString()
        viewsNumber.text = editPost.viewsCount.toString()
        likesButton?.setImageResource(getLikeIconResId(editPost.likedByMe))

    }

    private fun shareCounter(post: Post): Int {
        return post.shareCount + 1
    }

    private fun likeCounter(post: Post): Int {
        var likeCount = if (post.likedByMe) {
            post.likes + 1
        } else {
            post.likes - 1
        }
        return likeCount
    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24
        else R.drawable.ic_baseline_favorite_24

}




