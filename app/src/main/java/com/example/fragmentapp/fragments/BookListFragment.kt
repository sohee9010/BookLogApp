package com.example.fragmentapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentapp.MainActivity
import com.example.fragmentapp.R
import com.example.fragmentapp.adapters.BookAdapter
import com.example.fragmentapp.data.BookRepository
import com.example.fragmentapp.models.BookStatus

class BookListFragment : Fragment() {

    private var bookStatus: BookStatus? = null
    private val bookAdapter by lazy {
        BookAdapter { book ->
            // 클릭된 책 정보를 MainActivity로 전달하여 상세 화면을 열도록 요청
            (activity as? MainActivity)?.navigateToDetail(book)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bookStatus = it.getSerializable(ARG_BOOK_STATUS) as? BookStatus
        }

        parentFragmentManager.setFragmentResultListener(REQUEST_KEY, this) { _, _ ->
            loadBooks()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = bookAdapter

        loadBooks()
    }

    private fun loadBooks() {
        val status = bookStatus ?: return
        val books = BookRepository.getBooksByStatus(status)
        bookAdapter.submitList(books)

        view?.findViewById<TextView>(R.id.empty_view)?.isVisible = books.isEmpty()
    }

    companion object {
        const val REQUEST_KEY = "book_added"
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
