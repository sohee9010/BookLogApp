package com.example.fragmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentapp.adapters.BookListPagerAdapter
import com.example.fragmentapp.data.BookRepository
import com.example.fragmentapp.databinding.DialogAddBookBinding
import com.example.fragmentapp.databinding.FragmentHomeBinding
import com.example.fragmentapp.fragments.BookListFragment
import com.example.fragmentapp.models.BookStatus
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = BookListPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        binding.fabAddBook.setOnClickListener {
            showAddBookDialog()
        }
    }

    private fun showAddBookDialog() {
        val dialogBinding = DialogAddBookBinding.inflate(LayoutInflater.from(context))

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("책 추가")
            .setView(dialogBinding.root)
            .setNegativeButton("취소", null)
            .setPositiveButton("저장") { _, _ ->
                val title = dialogBinding.etBookTitle.text.toString()
                val author = dialogBinding.etBookAuthor.text.toString()

                if (title.isBlank() || author.isBlank()) {
                    Snackbar.make(binding.root, "제목과 저자는 필수 항목입니다.", Snackbar.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val memo = dialogBinding.etBookMemo.text.toString()
                val rating = dialogBinding.rbBookRating.rating
                val coverUrl = dialogBinding.etBookCoverUrl.text.toString().ifBlank { null }
                val currentTabStatus = when (binding.viewPager.currentItem) {
                    0 -> BookStatus.READING
                    1 -> BookStatus.COMPLETED
                    else -> BookStatus.WISHLIST
                }

                BookRepository.addBook(title, author, memo, rating, coverUrl, currentTabStatus)

                parentFragmentManager.setFragmentResult(BookListFragment.REQUEST_KEY_BOOK_CHANGED, Bundle())

                Snackbar.make(binding.root, "'${title}' 책을 추가했습니다.", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> "읽는 중"
            1 -> "완독"
            else -> "읽고 싶은 책"
        }
    }
}
