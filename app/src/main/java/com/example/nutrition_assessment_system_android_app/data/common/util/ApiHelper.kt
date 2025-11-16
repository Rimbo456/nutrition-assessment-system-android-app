package com.example.nutrition_assessment_system_android_app.data.common.util

import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object ApiHelper {

    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): Resource<R> {
        return try {
            val response = apiCall()

            if (response.isSuccessful && response.body() != null) {
                Resource.Success(transform(response.body()!!))
            } else {
                Resource.Error(parseErrorMessage(response))
            }
        } catch (e: HttpException) {
            Resource.Error("Network error: ${e.message()}")
        } catch (_: IOException) {
            Resource.Error("Connection failed. Please check your internet connection.")
        } catch (e: Exception) {
            Resource.Error("An unexpected error occurred: ${e.localizedMessage ?: "Unknown error"}")
        }
    }

    private fun <T> parseErrorMessage(response: Response<T>): String {
        val errorBody = response.errorBody()?.string()
        return try {
            val errorJson = Gson().fromJson(errorBody, Map::class.java)
            errorJson["message"]?.toString() ?: "Request failed"
        } catch (e: Exception) {
            "Request failed: ${response.message()}"
        }
    }
}
