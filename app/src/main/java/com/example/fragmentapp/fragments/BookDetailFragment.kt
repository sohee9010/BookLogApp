package com.example.fragmentapp.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import coil.load
import com.example.fragmentapp.R
import com.example.fragmentapp.models.Book
import com.google.android.material.appbar.CollapsingToolbarLayout

class BookDetailFragment : Fragment() {

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
    ): View? {
        // MainActivity의 툴바를 숨깁니다.
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Fragment가 사라질 때 MainActivity의 툴바를 다시 보여줍니다.
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookData = book ?: return

        // 툴바 설정 (액션바로 설정하지 않고, 뒤로가기 기능만 구현)
        val toolbar = view.findViewById<Toolbar>(R.id.detail_toolbar)
        toolbar.setNavigationOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        val collapsingToolbar = view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbar.title = bookData.title

        // 데이터 바인딩
        view.findViewById<ImageView>(R.id.iv_book_cover_large).load(bookData.coverImageUrl) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_background)
        }
        view.findViewById<TextView>(R.id.tv_detail_author).text = bookData.author
        view.findViewById<RatingBar>(R.id.rb_detail_rating).rating = bookData.rating
        view.findViewById<TextView>(R.id.tv_detail_memo).text = bookData.memo ?: "(메모 없음)"
    }

    companion object {
        const val ARG_BOOK = "book_arg"
    }
}
