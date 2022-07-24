package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.AppActivity.Companion.PostArgs
import ru.netology.nmedia.activity.PostFragment.Companion.KEY_ID
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding


class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val viewModel by viewModels<ru.netology.nmedia.viewmodel.PostViewModel>(ownerProducer = ::requireParentFragment)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
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
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply {
                    PostArgs =
                        EditPostResult(
                            postContentAndVideo?.content, postContentAndVideo?.video
                        )
                }
            )
        }

        viewModel.openPostEvent.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_postFragment,
                Bundle().apply {
                    putLong(KEY_ID, it)
                }
            )
        }
        return binding.root
    }
}
