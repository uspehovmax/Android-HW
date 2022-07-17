package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.PostContentBinding


class PostContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edit.requestFocus()

        val intent = intent
        binding.edit.setText(intent.getCharSequenceExtra("content"))
        binding.link.setText(intent.getCharSequenceExtra("video"))
        binding.save.setOnClickListener {
            val outIntent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, outIntent)
            } else {
                if(binding.link.text.isNotBlank()) {
                    val url = binding.link.text.toString()
                    outIntent.putExtra("url", url)
                }
                val content = binding.edit.text.toString()
                outIntent.putExtra("content", content)
                setResult(Activity.RESULT_OK, outIntent)
            }
            finish()
        }

        binding.cancel.setOnClickListener{
            finish()
        }
    }

    class EditPostResult(
        var newContent: String,
        var newVideoUrl: String?,
    )

    object ResultContract: ActivityResultContract<EditPostResult?, EditPostResult?>() {

        override fun createIntent(context: Context, input: EditPostResult?): Intent =
            Intent(context, PostContentActivity::class.java).apply {
                putExtra("content", input?.newContent)
                putExtra("video", input?.newVideoUrl)
            }

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if(resultCode == Activity.RESULT_OK) {
                EditPostResult(
                    newContent = intent?.getStringExtra("content")!!,
                    newVideoUrl = intent.getStringExtra("url"),
                )
            } else null
    }

}
