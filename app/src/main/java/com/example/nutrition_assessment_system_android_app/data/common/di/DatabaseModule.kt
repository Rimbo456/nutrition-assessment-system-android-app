package com.example.nutrition_assessment_system_android_app.data.common.di

import android.content.Context
import androidx.room.Room
import com.example.nutrition_assessment_system_android_app.data.common.database.AppDatabase
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserDao
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
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "nutrition_assessment_system_db"
        )
//            .addMigrations()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}