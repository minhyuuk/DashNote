package com.minhyuuk.dashnote.di.module

import android.content.Context
import androidx.room.Room
import com.minhyuuk.dashnote.data.database.MemoDatabase
import com.minhyuuk.dashnote.data.model.memo.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): MemoDatabase {
        return Room.databaseBuilder(
            context,
            MemoDatabase::class.java,
            MemoDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMemoDao(database: MemoDatabase): MemoDao {
        return database.memoDao()
    }
}