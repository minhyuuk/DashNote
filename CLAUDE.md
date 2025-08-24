⏺ DashNote 프로젝트 컨벤션 규칙

  기술 스택

  - Language: Kotlin
  - Framework: Android Jetpack Compose
  - Architecture: MVVM + Clean Architecture
  - DI: Hilt (Dagger)
  - Database: Room
  - Navigation: Navigation Compose
  - Logging: Timber

  Building Blocks

  1. 패키지 구조

  com.minhyuuk.dashnote/
  ├── data/
  │   └── model/
  │       └── memo/
  ├── di/
  ├── ui/
  │   ├── components/
  │   ├── extensions/
  │   ├── screen/
  │   │   ├── create/
  │   │   └── main/
  │   └── theme/

  2. 핵심 기능

  1. 메모 리스트 화면 (MemoListScreen): 검색, 정렬, 메모 표시
  2. 메모 생성 및 수정 화면 (MemoEditScreen): 제목/내용 입력
  3. 메모 카드 컴포넌트 (MemoCard): 스와이프 삭제 기능

  코드 가이드라인

  1. Composable 함수 규칙

  - @OptIn(ExperimentalMaterial3Api::class) 주석 사용 (Material3 API 사용 시)
  - Preview 함수 필수 포함 (@Preview(showBackground = true))
  - 매개변수에 기본값 제공 (onCreateMemoClick: () -> Unit = {})

  2. UI 상태 관리

  var searchText by remember { mutableStateOf("") }
  var selectedSortOrder by remember { mutableStateOf("최신순") }
  var expanded by remember { mutableStateOf(false) }

  3. 레이아웃 패턴

  - Scaffold 기반 구조 (TopAppBar + Content + FloatingActionButton)
  - Column/Row + Modifier 체이닝
  - padding 값: 주로 8dp, 16dp, 20dp 사용
  - Shape: RoundedCornerShape(4dp), RoundedCornerShape(10dp)

  4. 스타일링 규칙

  - MaterialTheme.colorScheme 색상 사용
  - FontWeight: Bold, Medium 사용
  - fontSize: 12sp~30sp 범위
  - Custom Extension: dashedBorder() 확장 함수 활용

  Naming Convention

  1. 파일명

  - Screen: {기능명}Screen.kt (예: MemoListScreen.kt, MemoCreateScreen.kt)
  - Component: {컴포넌트명}.kt (예: MemoCard.kt)
  - Data Model: {모델명}Data.kt (예: MemoData.kt)
  - Extension: {타입명}Extensions.kt (예: ModifierExtensions.kt)

  2. 변수명

  - State 변수: {기능명}Text (예: titleText, contentText, searchText)
  - Boolean 변수: is{상태명}, {동작명}ed (예: isFirstItem, expanded)
  - Callback 함수: on{액션명}Click (예: onCreateMemoClick, onDeleteClick)

  3. 리소스명

  - Drawable: baseline_{아이콘명}_24 (예: baseline_note_24.xml)
  - String: {기능명}_{설명} (예: search_note_hint, write_title)

  4. 패키지명

  - 소문자 + 단수형 사용
  - 기능별 분리: screen, component, theme, extension, model

  5. Composable 함수명

  - PascalCase 사용
  - 명사형 (예: DashNoteApp, MemoListScreen, MemoCard)