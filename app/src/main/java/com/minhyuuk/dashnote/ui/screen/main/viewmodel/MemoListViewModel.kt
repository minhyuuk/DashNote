package com.minhyuuk.dashnote.ui.screen.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhyuuk.dashnote.data.model.memo.MemoData
import com.minhyuuk.dashnote.data.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MemoListViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel(), MemoListViewModelInterface {

    private val _searchText = MutableStateFlow("")
    override val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _sortOrder = MutableStateFlow("최신순")
    override val sortOrder: StateFlow<String> = _sortOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    override val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    override val memos: StateFlow<List<MemoData>> = combine(
        _searchText,
        _sortOrder
    ) { searchText, sortOrder ->
        Pair(searchText, sortOrder)
    }.flatMapLatest { (searchText, sortOrder) ->
        val memoFlow = if (searchText.isEmpty()) {
            memoRepository.getAllMemos()
        } else {
            memoRepository.searchMemos(searchText)
        }
        
        memoFlow.map { memoList ->
            _isLoading.value = false
            Timber.d("메모 리스트 로드 완료: ${memoList.size}개")
            
            when (sortOrder) {
                "최신순" -> memoList.sortedByDescending { it.id }
                "제목순" -> memoList.sortedBy { it.title?.lowercase() }
                else -> memoList
            }
        }
    }.let { flow ->
        MutableStateFlow(emptyList<MemoData>()).apply {
            viewModelScope.launch {
                flow.collect { 
                    value = it 
                }
            }
        }.asStateFlow()
    }

    override fun updateSearchText(text: String) {
        Timber.d("검색 텍스트 업데이트: '$text'")
        _searchText.value = text
    }

    override fun updateSortOrder(order: String) {
        Timber.d("정렬 순서 변경: $order")
        _sortOrder.value = order
    }

    override fun deleteMemo(memo: MemoData) {
        viewModelScope.launch {
            try {
                Timber.d("메모 삭제 시작: ${memo.title}")
                memoRepository.deleteMemo(memo)
                Timber.i("메모 삭제 완료: ID=${memo.id}, 제목='${memo.title}'")
            } catch (e: Exception) {
                Timber.e(e, "메모 삭제 실패: ${memo.title}")
            }
        }
    }

    override fun deleteMemoById(id: Long) {
        viewModelScope.launch {
            try {
                Timber.d("메모 삭제 시작: ID=$id")
                memoRepository.deleteMemoById(id)
                Timber.i("메모 삭제 완료: ID=$id")
            } catch (e: Exception) {
                Timber.e(e, "메모 삭제 실패: ID=$id")
            }
        }
    }
}