package com.minhyuuk.dashnote.ui.screen.edit.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.minhyuuk.dashnote.data.repository.MemoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject



@HiltViewModel
class MemoEditViewModel @Inject constructor(
    private val memoRepository: MemoRepository
) : ViewModel(), MemoEditViewModelInterface {

    private val _title = MutableStateFlow("")
    override val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
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
        viewModelScope.launch {
            val titleText = _title.value.trim()
            val descriptionText = _description.value.trim()

            if (titleText.isNotEmpty() || descriptionText.isNotEmpty()) {
                _isSaving.value = true
                try {
                    Timber.d("Room 작업 시작 - 메모 저장 : title='$titleText', description='$descriptionText'")
                    val result = memoRepository.insertMemo(titleText, descriptionText)
                    Timber.d("Room 작업 완료 - 메모 저장 성공 : ID=$result")
                } catch (e: Exception) {
                    Timber.e(e, "Room 작업 실패 - 메모 저장 에러")
                } finally {
                    _isSaving.value = false
                }
            } else {
                Timber.d("Room 작업 생략 - 빈 메모")
            }
            onComplete()
        }
    }

    override fun hasContent(): Boolean {
        return _title.value.trim().isNotEmpty() || _description.value.trim().isNotEmpty()
    }
}

