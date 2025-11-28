package com.example.fragmentapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.fragmentapp.R
import com.example.fragmentapp.databinding.ItemBookBinding
import com.example.fragmentapp.models.Book

class BookAdapter(
    private val onItemClicked: (Book) -> Unit
) : ListAdapter<Book, BookAdapter.BookViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(book)
        }
        holder.bind(book)
    }

    // ViewHolder가 ItemBookBinding을 직접 사용하도록 수정
    inner class BookViewHolder(private val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            binding.tvBookTitle.text = book.title
            binding.tvBookAuthor.text = book.author
            binding.rbBookRating.rating = book.rating

            binding.ivBookCover.load(book.coverImageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            }

            if (book.memo.isNullOrBlank()) {
                binding.tvBookMemo.visibility = View.GONE
            } else {
                binding.tvBookMemo.visibility = View.VISIBLE
                binding.tvBookMemo.text = book.memo
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
