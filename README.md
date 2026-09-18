# 📚 담북 (Dambook) : Smart Library Manager

## 🎀 프로젝트 소개

🏷 **프로젝트 명 : 담북(Dambook) - 독서 관리 앱**

🗓️ **개발 기간 : 26.01.02 ~ 26.01.29**

👤 **개인 프로젝트 (1인 개발) : 박소희**

---

### ✅ 기획 배경

> 읽고 싶은 책, 읽는 중인 책, 다 읽은 책… 흩어진 독서 기록을 한곳에 담을 수 있다면?

여러 곳에 흩어지기 쉬운 독서 기록을 한 곳에 모으고, 단순히 "읽었다/안 읽었다"를 넘어 **월별 독서량과 상태 비율을 시각화**해 스스로의 독서 습관을 직관적으로 파악할 수 있는 안드로이드 앱을 기획했다.

### ✅ 서비스 소개

> **카카오 도서 검색 API로 책을 등록하고, 독서 상태와 통계를 관리하는 안드로이드 스마트 서재 앱**

- 카카오 도서 검색 API 연동으로 책 정보를 간편하게 불러와 등록
- 읽고 싶은 / 읽는 중 / 완독 상태를 체계적으로 기록·관리
- MPAndroidChart 기반 월별 독서량·상태 비율 그래프로 독서 습관 시각화
- 개인 독서 노트 및 평점 기록

### 👥 서비스 대상

- 여러 권의 책을 동시에 관리하고 싶은 다독가
- 독서 기록을 데이터로 시각화해서 습관을 돌아보고 싶은 사람들

---

## 💌 화면 및 기능 소개

### ✅ 도서 검색 및 등록

> 카카오 도서 검색 API로 제목/저자를 검색해 책 정보를 바로 불러와 서재에 등록한다.

`(도서 검색/등록 화면 스크린샷 추가 예정)`

### ✅ 독서 상태 관리

> 읽고 싶은 / 읽는 중 / 완독 세 가지 상태로 책을 분류하고 관리한다.

`(서재/상태별 목록 화면 스크린샷 추가 예정)`

### ✅ 독서 통계 시각화

> MPAndroidChart로 월별 독서량과 상태별 비율을 그래프로 보여준다.

`(통계 화면 스크린샷 추가 예정)`

### ✅ 독서 노트 & 평점

> 책마다 개인 노트와 평점을 기록해 나만의 독서 기록을 남긴다.

`(노트/평점 화면 스크린샷 추가 예정)`

---

## 🛠 기술 스택

### App

![](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![](https://img.shields.io/badge/Android_Studio-3DDC84?style=flat-square&logo=androidstudio&logoColor=white)

### Database & Library

![](https://img.shields.io/badge/Room_DB-4285F4?style=flat-square&logo=sqlite&logoColor=white)
![](https://img.shields.io/badge/MPAndroidChart-FF6F00?style=flat-square)
![](https://img.shields.io/badge/카카오_도서_검색_API-FFCD00?style=flat-square&logo=kakao&logoColor=black)

> 기기 내 Room DB에 도서 데이터를 저장하고, 카카오 도서 검색 API로 책 정보를 가져와 등록한다. MPAndroidChart로 독서 통계를 그래프로 시각화했다.

---

## 🗂 프로젝트 구조

```
└─📦 BookLogApp
  ├─📂 app
  │  └─📂 src/main
  │    ├─📂 java/                # Kotlin 소스 (검색/등록, 상태 관리, 통계, 노트)
  │    └─📂 res/                 # 레이아웃, 리소스
  ├─📂 gradle
  ├─📜 build.gradle.kts
  └─📜 settings.gradle.kts
```

---

## 💬 담당 역할

- 카카오 도서 검색 API 등 외부 연동 및 기능 구현
- MPAndroidChart 기반 데이터 시각화 및 통계 분석 로직
