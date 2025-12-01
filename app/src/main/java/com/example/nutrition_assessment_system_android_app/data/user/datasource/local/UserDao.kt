package com.example.nutrition_assessment_system_android_app.data.user.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
//    @Query("select * from users")
//    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("select * from weight_logs order by timestamp desc")
    fun getAllWeightLogs(): Flow<List<WeightLogEntity>>

    @Insert
    suspend fun insertWeightLogs(weightLogEntities: List<WeightLogEntity>)
}