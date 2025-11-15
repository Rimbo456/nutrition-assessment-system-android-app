package com.example.nutrition_assessment_system_android_app.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.nutrition_assessment_system_android_app.data.common.util.Converters
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserDao
import com.example.nutrition_assessment_system_android_app.data.user.datasource.local.UserEntity

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}