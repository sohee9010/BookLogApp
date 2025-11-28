package com.example.fragmentapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fragmentapp.fragments.BookListFragment
import com.example.fragmentapp.models.BookStatus

/**
 * HomeFragment의 ViewPager2를 위한 어댑터
 */
class BookListPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    // 탭의 개수
    override fun getItemCount(): Int = 3

    // 각 탭 위치(position)에 어떤 Fragment를 보여줄지 결정
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BookListFragment.newInstance(BookStatus.READING)
            1 -> BookListFragment.newInstance(BookStatus.COMPLETED)
            else -> BookListFragment.newInstance(BookStatus.WISHLIST)
        }
    }
}
