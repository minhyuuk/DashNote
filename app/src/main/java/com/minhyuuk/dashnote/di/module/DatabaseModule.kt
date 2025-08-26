package com.minhyuuk.dashnote.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.minhyuuk.dashnote.BuildConfig
import com.minhyuuk.dashnote.data.database.MemoDatabase
import com.minhyuuk.dashnote.data.model.memo.MemoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.concurrent.Executors
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
        ).apply {
            if (BuildConfig.DEBUG) {
                setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                    Timber.d("Room SQL 쿼리 : $sqlQuery")
                    Timber.d("Room Bind Args : $bindArgs")
                }, Executors.newSingleThreadExecutor())
            }
        }.build()
    }

    @Provides
    fun provideMemoDao(database: MemoDatabase): MemoDao {
        return database.memoDao()
    }
}