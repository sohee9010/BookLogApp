package com.example.fragmentapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fragmentapp.MainActivity
import com.example.fragmentapp.adapters.BookAdapter
import com.example.fragmentapp.data.BookRepository
import com.example.fragmentapp.databinding.FragmentBookListBinding
import com.example.fragmentapp.models.BookStatus

class BookListFragment : Fragment() {

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!

    private var bookStatus: BookStatus? = null
    private val bookAdapter by lazy {
        BookAdapter { book ->
            (activity as? MainActivity)?.navigateToDetail(book)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // 안드로이드 버전에 맞는 올바른 getSerializable 사용
            bookStatus = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(ARG_BOOK_STATUS, BookStatus::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getSerializable(ARG_BOOK_STATUS) as? BookStatus
            }
        }

        parentFragmentManager.setFragmentResultListener(REQUEST_KEY_BOOK_CHANGED, this) { _, _ ->
            loadBooks()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = bookAdapter

        loadBooks()
    }

    private fun loadBooks() {
        val status = bookStatus ?: return
        val books = BookRepository.getBooksByStatus(status)
        bookAdapter.submitList(books)

        binding.emptyView.isVisible = books.isEmpty()
    }

    companion object {
        const val REQUEST_KEY_BOOK_CHANGED = "book_changed"
        private const val ARG_BOOK_STATUS = "book_status"

        @JvmStatic
        fun newInstance(bookStatus: BookStatus) =
            BookListFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_BOOK_STATUS, bookStatus)
                }
            }
    }
}
