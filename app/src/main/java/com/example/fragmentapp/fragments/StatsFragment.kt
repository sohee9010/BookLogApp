package com.example.fragmentapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentapp.data.BookRepository
import com.example.fragmentapp.databinding.FragmentStatsBinding
import com.example.fragmentapp.models.BookStatus

class StatsFragment : Fragment() {

    private var _binding: FragmentStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // 화면이 다시 보일 때마다 통계를 새로고침합니다.
        updateStats()
    }

    private fun updateStats() {
        val allBooks = BookRepository.getAllBooks()

        // 1. 상태별 책 권수 계산
        val completedCount = allBooks.count { it.status == BookStatus.COMPLETED }
        val readingCount = allBooks.count { it.status == BookStatus.READING }
        val wishlistCount = allBooks.count { it.status == BookStatus.WISHLIST }

        // 2. 완독한 책 평균 별점 계산
        val completedBooks = allBooks.filter { it.status == BookStatus.COMPLETED }
        val averageRating = if (completedBooks.isNotEmpty()) {
            completedBooks.map { it.rating }.average().toFloat()
        } else {
            0.0f
        }

        // 3. UI에 통계 데이터 표시
        binding.tvCompletedCount.text = completedCount.toString()
        binding.rbAverageRating.rating = averageRating
        binding.tvReadingCount.text = readingCount.toString()
        binding.tvWishlistCount.text = wishlistCount.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
