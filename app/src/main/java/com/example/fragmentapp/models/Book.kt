package com.example.fragmentapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 책 데이터 모델 클래스
 * @Parcelize 어노테이션을 통해 객체를 Bundle에 담아 Fragment 간에 전달할 수 있습니다.
 *
 * @param id 고유 식별자
 * @param title 제목
 * @param author 저자
 * @param memo 메모
 * @param rating 평점 (0.0f ~ 5.0f)
 * @param coverImageUrl 책 커버 이미지 URL
 * @param status 책의 상태 (읽는 중, 완독, 읽고 싶은 책)
 */
@Parcelize
data class Book(
    val id: Long,
    val title: String,
    val author: String,
    val memo: String? = null,
    val rating: Float = 0.0f,
    val coverImageUrl: String? = null, // 이미지 URL 속성 추가
    val status: BookStatus
) : Parcelable

/**
 * 책의 상태를 나타내는 Enum 클래스
 */
enum class BookStatus {
    READING,    // 읽는 중
    COMPLETED,  // 완독
    WISHLIST    // 읽고 싶은 책
}
