package com.example.fragmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.fragmentapp.adapters.BookListPagerAdapter
import com.example.fragmentapp.data.BookRepository
import com.example.fragmentapp.fragments.BookListFragment
import com.example.fragmentapp.models.BookStatus
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = BookListPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()

        view.findViewById<FloatingActionButton>(R.id.fab_add_book).setOnClickListener {
            showAddBookDialog()
        }
    }

    private fun showAddBookDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_book, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.et_book_title)
        val authorEditText = dialogView.findViewById<EditText>(R.id.et_book_author)
        val coverUrlEditText = dialogView.findViewById<EditText>(R.id.et_book_cover_url) // 이미지 URL EditText
        val memoEditText = dialogView.findViewById<EditText>(R.id.et_book_memo)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.rb_book_rating)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("책 추가")
            .setView(dialogView)
            .setNegativeButton("취소", null)
            .setPositiveButton("저장") { _, _ ->
                val title = titleEditText.text.toString()
                val author = authorEditText.text.toString()

                if (title.isBlank() || author.isBlank()) {
                    Snackbar.make(requireView(), "제목과 저자는 필수 항목입니다.", Snackbar.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val memo = memoEditText.text.toString()
                val rating = ratingBar.rating
                val coverUrl = coverUrlEditText.text.toString().ifBlank { null } // 비어있으면 null로 저장
                val currentTabStatus = when (viewPager.currentItem) {
                    0 -> BookStatus.READING
                    1 -> BookStatus.COMPLETED
                    else -> BookStatus.WISHLIST
                }

                BookRepository.addBook(title, author, memo, rating, coverUrl, currentTabStatus)

                parentFragmentManager.setFragmentResult(BookListFragment.REQUEST_KEY, Bundle())

                Snackbar.make(requireView(), "'${title}' 책을 추가했습니다.", Snackbar.LENGTH_SHORT).show()
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
