package com.minhyuuk.dashnote.ui.screen.edit.viewmodel

import com.minhyuuk.dashnote.data.model.memo.MemoData
import kotlinx.coroutines.flow.StateFlow

interface MemoEditViewModelInterface {
    val title: StateFlow<String>
    val description: StateFlow<String>
    val isSaving: StateFlow<Boolean>

    fun updateTitle(newTitle: String)
    fun updateDescription(newDescription: String)
    fun saveMemo(onComplete: () -> Unit)
    fun hasContent(): Boolean
    fun loadMemo(memoData: MemoData)
    fun loadMemoById(memoId: Long)
}