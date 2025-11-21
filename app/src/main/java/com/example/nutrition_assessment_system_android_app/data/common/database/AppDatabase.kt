package com.example.nutrition_assessment_system_android_app.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.ChatSessionDao
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.ChatSessionEntity
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.MessageDao
import com.example.nutrition_assessment_system_android_app.data.chat.datasource.local.MessageEntity
import com.example.nutrition_assessment_system_android_app.data.common.util.Converters
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserDao
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserEntity

@Database(
    entities = [UserEntity::class, MessageEntity::class, ChatSessionEntity::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun chatSessionDao(): ChatSessionDao
}