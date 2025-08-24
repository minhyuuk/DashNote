package com.minhyuuk.dashnote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.minhyuuk.dashnote.data.model.memo.MemoData
import com.minhyuuk.dashnote.data.model.memo.MemoDao

@Database(
    entities = [MemoData::class],
    version = 1,
    exportSchema = false
)
abstract class MemoDatabase : RoomDatabase() {
    abstract fun memoDao(): MemoDao
    
    companion object {
        const val DATABASE_NAME = "dashnote_database"
    }
}