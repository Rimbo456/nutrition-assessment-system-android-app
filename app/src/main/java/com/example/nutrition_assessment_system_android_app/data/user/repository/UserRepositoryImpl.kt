package com.example.nutrition_assessment_system_android_app.data.user.repository

import android.util.Log
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.UserApiService
import com.example.nutrition_assessment_system_android_app.data.user.datasource.remote.request.RegisterRequest
import com.example.nutrition_assessment_system_android_app.data.user.mapper.toUser
import com.example.nutrition_assessment_system_android_app.domain.model.User
import com.example.nutrition_assessment_system_android_app.domain.repository.UserRepository
import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRepository {

    override suspend fun registerUser(
        name: String,
        email: String,
        password: String
    ): Resource<User> {
        return try {
            val response = userApiService.registerUser(
                RegisterRequest(name, email, password)
            )

            if (response.isSuccessful && response.body() != null) {
                val userDto = response.body()!!.user
                Resource.Success(userDto.toUser())
            } else {
                // Parse error body to get server error message
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val errorJson = Gson().fromJson(errorBody, Map::class.java)
                    errorJson["message"]?.toString() ?: "Registration failed"
                } catch (e: Exception) {
                    "Registration failed: ${response.message()}"
                }
                Resource.Error(errorMessage)
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (_: IOException) {
            Resource.Error("Connection failed. Please check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.localizedMessage ?: "Unknown error"}")
        }
    }
}