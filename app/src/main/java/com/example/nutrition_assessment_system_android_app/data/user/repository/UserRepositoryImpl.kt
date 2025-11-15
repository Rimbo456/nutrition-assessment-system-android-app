package com.example.nutrition_assessment_system_android_app.data.user.repository

import com.example.nutrition_assessment_system_android_app.data.common.util.NetworkBoundResource
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.dto.UserDto
import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
//    private val userDao: UserDao,
//    private val userApiService: UserApiService
) : UserRepository {
    override fun getAllUsers(forceRefresh: Boolean): Flow<Resource<List<User>>> {
        return object : NetworkBoundResource<List<User>, List<UserDto>>() {
            override fun loadFromDb(): Flow<List<User>> {
                TODO("Not yet implemented")
            }

            override fun shouldFetch(data: List<User>?): Boolean {
                TODO("Not yet implemented")
            }

            override suspend fun createCall(): List<UserDto> {
                TODO("Not yet implemented")
            }

            override suspend fun saveCallResult(data: List<UserDto>) {
                TODO("Not yet implemented")
            }
        }.asFlow()
    }
}