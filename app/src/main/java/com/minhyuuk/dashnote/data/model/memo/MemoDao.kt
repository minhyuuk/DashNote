package com.minhyuuk.dashnote.data.model.memo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoDao {
    @Query("SELECT * FROM memo ORDER BY id DESC")
    fun getAllMemos(): Flow<List<MemoData>>
    
    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun getMemoById(id: Long): MemoData?
    
    @Insert
    suspend fun insertMemo(memo: MemoData): Long
    
    @Update
    suspend fun updateMemo(memo: MemoData)
    
    @Delete
    suspend fun deleteMemo(memo: MemoData)
    
    @Query("DELETE FROM memo WHERE id = :id")
    suspend fun deleteMemoById(id: Long)
    
    @Query("SELECT * FROM memo WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' ORDER BY id DESC")
    fun searchMemos(query: String): Flow<List<MemoData>>
}