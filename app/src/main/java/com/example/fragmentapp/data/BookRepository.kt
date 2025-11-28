package com.example.fragmentapp.data

import com.example.fragmentapp.models.Book
import com.example.fragmentapp.models.BookStatus

/**
 * 앱의 전체 책 데이터를 관리하는 싱글턴 객체 (임시 데이터베이스 역할)
 */
object BookRepository {

    private val books = mutableListOf<Book>()
    private var nextId = 1L

    init {
        addInitialBooks()
    }

    fun getBooksByStatus(status: BookStatus): List<Book> {
        return books.filter { it.status == status }
    }

    fun addBook(title: String, author: String, memo: String?, rating: Float, coverImageUrl: String?, status: BookStatus) {
        val newBook = Book(
            id = nextId++,
            title = title,
            author = author,
            memo = memo,
            rating = rating,
            coverImageUrl = coverImageUrl,
            status = status
        )
        books.add(0, newBook) // 새 책을 맨 위에 추가
    }

    private fun addInitialBooks() {
        addBook("데미안", "헤르만 헤세", "새는 알에서 나오려고 투쟁한다.", 4.5f, "https://image.aladin.co.kr/product/1/1/cover/s082834371_1.jpg", BookStatus.COMPLETED)
        addBook("어린왕자", "생텍쥐페리", "가장 중요한 것은 눈에 보이지 않아.", 5.0f, "https://image.aladin.co.kr/product/19/3/cover/8932917248_2.jpg", BookStatus.COMPLETED)
        addBook("이방인", "알베르 카뮈", null, 4.0f, "https://image.aladin.co.kr/product/27386/3/cover/8937462446_1.jpg", BookStatus.READING)
        addBook("코스모스", "칼 세이건", "우리는 코스모스의 일부이다.", 5.0f, "https://image.aladin.co.kr/product/13/4/cover/8983719246_4.jpg", BookStatus.READING)
        addBook("총, 균, 쇠", "재레드 다이아몬드", null, 4.5f, "https://image.aladin.co.kr/product/31/7/cover/8970131920_1.jpg", BookStatus.WISHLIST)
        addBook("사피엔스", "유발 하라리", "역사의 방향을 결정한 것은 무엇인가?", 4.8f, "https://image.aladin.co.kr/product/6735/9/cover/8934925141_2.jpg", BookStatus.WISHLIST)
    }
}
