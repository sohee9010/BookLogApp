package com.example.fragmentapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fragmentapp.R
import com.example.fragmentapp.models.Book

/**
 * 책 목록을 표시하기 위한 RecyclerView 어댑터
 */
class BookAdapter(
    private val onItemClicked: (Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        // 아이템 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            onItemClicked(book)
        }
        holder.bind(book)
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val coverImageView: ImageView = itemView.findViewById(R.id.iv_book_cover)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_book_title)
        private val authorTextView: TextView = itemView.findViewById(R.id.tv_book_author)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.rb_book_rating)
        private val memoTextView: TextView = itemView.findViewById(R.id.tv_book_memo)

        fun bind(book: Book) {
            titleTextView.text = book.title
            authorTextView.text = book.author
            ratingBar.rating = book.rating

            coverImageView.load(book.coverImageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            }

            if (book.memo.isNullOrBlank()) {
                memoTextView.visibility = View.GONE
            } else {
                memoTextView.visibility = View.VISIBLE
                memoTextView.text = book.memo
            }
        }
    }
}

class BookDiffCallback : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem
    }
}
