package com.minhyuuk.dashnote.ui.screen.edit.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FakeMemoEditViewModel : MemoEditViewModelInterface {
    private val _title = MutableStateFlow("아침 일찍 일어나보아요.")
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("일찍 일어나는게 중요한게 아닙니다. 약속한 시간에 자고 약속한 시간에 일어나 나의 생체 리듬을 안정하게 만드는 것이 중요합니다.")
    override val description: StateFlow<String> = _description.asStateFlow()

    private val _isSaving = MutableStateFlow(false)
    override val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    override fun updateTitle(newTitle: String) {
        _title.value = newTitle
    }

    override fun updateDescription(newDescription: String) {
        _description.value = newDescription
    }

    override fun saveMemo(onComplete: () -> Unit) {
        _isSaving.value = true
        onComplete()
        _isSaving.value = false
    }

    override fun hasContent(): Boolean {
        return _title.value.trim().isNotEmpty() || _description.value.trim().isNotEmpty()
    }
}