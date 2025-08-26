package com.minhyuuk.dashnote.ui.screen.main.viewmodel

import com.minhyuuk.dashnote.data.model.memo.MemoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FakeMemoListViewModel : MemoListViewModelInterface {
    
    private val _memos = MutableStateFlow(
        listOf(
            MemoData(
                id = 1,
                title = "첫 번째 메모",
                description = "이것은 첫 번째 메모의 내용입니다. 메모 앱을 테스트하기 위한 샘플 데이터입니다.",
                createdDate = "2023.10.15",
                createdTime = "14:30"
            ),
            MemoData(
                id = 2,
                title = "두 번째 메모",
                description = "두 번째 메모의 내용입니다. 더 긴 텍스트를 테스트하기 위해 작성되었습니다.",
                createdDate = "2023.10.14",
                createdTime = "09:15"
            ),
            MemoData(
                id = 3,
                title = "할 일 목록",
                description = "1. 코드 리뷰\n2. 회의 참석\n3. 문서 작성\n4. 테스트 케이스 추가",
                createdDate = "2023.10.13",
                createdTime = "16:45"
            )
        )
    )
    override val memos: StateFlow<List<MemoData>> = _memos.asStateFlow()

    private val _searchText = MutableStateFlow("")
    override val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _sortOrder = MutableStateFlow("최신순")
    override val sortOrder: StateFlow<String> = _sortOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    override fun updateSearchText(text: String) {
        _searchText.value = text
    }

    override fun updateSortOrder(order: String) {
        _sortOrder.value = order
    }

    override fun deleteMemo(memo: MemoData) {
        _memos.value = _memos.value.filter { it.id != memo.id }
    }

    override fun deleteMemoById(id: Long) {
        _memos.value = _memos.value.filter { it.id != id }
    }
}