package com.example.fragmentapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.example.fragmentapp.R
import com.example.fragmentapp.data.BookRepository
import com.example.fragmentapp.databinding.DialogAddBookBinding
import com.example.fragmentapp.databinding.FragmentBookDetailBinding
import com.example.fragmentapp.models.Book
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class BookDetailFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    private var book: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        book = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_BOOK, Book::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable(ARG_BOOK)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bookData = book ?: return

        setupToolbar(bookData)
        bindBookData(bookData)
    }

    private fun setupToolbar(book: Book) {
        binding.detailToolbar.setNavigationOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }
        binding.detailToolbar.inflateMenu(R.menu.detail_menu)
        binding.detailToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    showEditBookDialog(book)
                    true
                }
                R.id.action_delete -> {
                    showDeleteConfirmationDialog(book)
                    true
                }
                else -> false
            }
        }
    }

    private fun bindBookData(book: Book) {
        binding.collapsingToolbar.title = book.title
        binding.ivBookCoverLarge.load(book.coverImageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_background)
        }
        binding.tvDetailAuthor.text = book.author
        binding.rbDetailRating.rating = book.rating
        binding.tvDetailMemo.text = book.memo ?: "(메모 없음)"
    }

    private fun showDeleteConfirmationDialog(book: Book) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("삭제 확인")
            .setMessage("'${book.title}' 독서 기록을 정말 삭제하시겠습니까?")
            .setNegativeButton("취소", null)
            .setPositiveButton("삭제") { _, _ ->
                BookRepository.deleteBook(book)
                parentFragmentManager.setFragmentResult(BookListFragment.REQUEST_KEY_BOOK_CHANGED, Bundle())
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            .show()
    }

    private fun showEditBookDialog(bookToEdit: Book) {
        val dialogBinding = DialogAddBookBinding.inflate(LayoutInflater.from(context))

        dialogBinding.etBookTitle.setText(bookToEdit.title)
        dialogBinding.etBookAuthor.setText(bookToEdit.author)
        dialogBinding.etBookCoverUrl.setText(bookToEdit.coverImageUrl)
        dialogBinding.etBookMemo.setText(bookToEdit.memo)
        dialogBinding.rbBookRating.rating = bookToEdit.rating

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("책 정보 수정")
            .setView(dialogBinding.root)
            .setNegativeButton("취소", null)
            .setPositiveButton("저장") { _, _ ->
                val newTitle = dialogBinding.etBookTitle.text.toString()
                val newAuthor = dialogBinding.etBookAuthor.text.toString()

                if (newTitle.isBlank() || newAuthor.isBlank()) {
                    Snackbar.make(binding.root, "제목과 저자는 필수 항목입니다.", Snackbar.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val updatedBook = bookToEdit.copy(
                    title = newTitle,
                    author = newAuthor,
                    memo = dialogBinding.etBookMemo.text.toString().ifBlank { null },
                    rating = dialogBinding.rbBookRating.rating,
                    coverImageUrl = dialogBinding.etBookCoverUrl.text.toString().ifBlank { null }
                )

                BookRepository.updateBook(updatedBook)

                this.book = updatedBook
                bindBookData(updatedBook)

                parentFragmentManager.setFragmentResult(BookListFragment.REQUEST_KEY_BOOK_CHANGED, Bundle())

                Snackbar.make(binding.root, "'${updatedBook.title}' 정보를 수정했습니다.", Snackbar.LENGTH_SHORT).show()
            }
            .show()
    }

    companion object {
        const val ARG_BOOK = "book_arg"
    }
}
