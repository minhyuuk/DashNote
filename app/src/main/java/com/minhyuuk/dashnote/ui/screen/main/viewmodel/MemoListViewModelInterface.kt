package com.minhyuuk.dashnote.ui.screen.main.viewmodel

import com.minhyuuk.dashnote.data.model.memo.MemoData
import kotlinx.coroutines.flow.StateFlow

interface MemoListViewModelInterface {
    val memos: StateFlow<List<MemoData>>
    val searchText: StateFlow<String>
    val sortOrder: StateFlow<String>
    val isLoading: StateFlow<Boolean>
    
    fun updateSearchText(text: String)
    fun updateSortOrder(order: String)
    fun deleteMemo(memo: MemoData)
    fun deleteMemoById(id: Long)
}