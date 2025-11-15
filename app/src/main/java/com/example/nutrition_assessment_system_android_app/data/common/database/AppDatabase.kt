package com.example.nutrition_assessment_system_android_app.data.common.database

import androidx.room.Database
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserDao
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun userDao(): UserDao
}