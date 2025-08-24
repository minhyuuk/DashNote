package com.minhyuuk.dashnote.data.model.memo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo")
data class MemoData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String? = "",
    val description: String? = "",
    val createdDate: String,
    val createdTime: String
)