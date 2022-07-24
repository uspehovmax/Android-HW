package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity.Companion.PostArgs
import ru.netology.nmedia.databinding.FragmentPostBinding

class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        val viewModel by viewModels<ru.netology.nmedia.viewmodel.PostViewModel>(ownerProducer = ::requireParentFragment)

        val viewHolder = PostViewHolder(binding.cardPost, viewModel)
        val id = arguments?.getLong(KEY_ID)

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            posts.firstOrNull { it.id == id }?.let {
                viewHolder.bind(it)
                return@observe
            }
            findNavController().navigateUp()
        }

        viewModel.sharePostContent.observe(viewLifecycleOwner) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent =
                Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.playVideo.observe(viewLifecycleOwner) { videoUrl ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(context, "Wrong video url", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { postContentAndVideo ->
            findNavController().navigate(
                R.id.action_postFragment_to_newPostFragment,
                Bundle().apply {
                    PostArgs =
                        EditPostResult(
                            postContentAndVideo?.content,
                            postContentAndVideo?.video
                        )
                }
            )
        }

//        viewModel.openPostEvent.observe(viewLifecycleOwner) {
//
//        }

        return binding.root
    }

    companion object {
        const val KEY_ID = "id"
    }

}