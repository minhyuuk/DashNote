package com.minhyuuk.dashnote.data.repository

import com.minhyuuk.dashnote.data.model.memo.MemoDao
import com.minhyuuk.dashnote.data.model.memo.MemoData
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoRepository @Inject constructor(
    private val memoDao: MemoDao
) {
    fun getAllMemos(): Flow<List<MemoData>> = memoDao.getAllMemos()
    
    suspend fun getMemoById(id: Long): MemoData? = memoDao.getMemoById(id)
    
    suspend fun insertMemo(title: String, description: String): Long {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        
        val memo = MemoData(
            title = title.trim(),
            description = description.trim(),
            createdDate = dateFormat.format(Date(currentTime)),
            createdTime = timeFormat.format(Date(currentTime))
        )
        
        return memoDao.insertMemo(memo)
    }
    
    suspend fun updateMemo(memo: MemoData) = memoDao.updateMemo(memo)
    
    suspend fun deleteMemo(memo: MemoData) = memoDao.deleteMemo(memo)
    
    suspend fun deleteMemoById(id: Long) = memoDao.deleteMemoById(id)
    
    fun searchMemos(query: String): Flow<List<MemoData>> = memoDao.searchMemos(query)
}