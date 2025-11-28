package com.example.nutrition_assessment_system_android_app.data.common.util

import android.util.Log
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
                // Debug log: serialized success body before mapping
                try {
                    Log.d("API_DEBUG", "Success raw body: ${Gson().toJson(response.body())}")
                } catch (_: Exception) {}
                Resource.Success(transform(response.body()!!))
            } else {
                // Debug log: error body
                val errorStr = response.errorBody()?.string()
                Log.d("API_DEBUG", "Error raw body: $errorStr")
                Resource.Error(parseErrorMessage(response))
            }
        } catch (e: HttpException) {
            Log.d("API_DEBUG", "HttpException: ${e.message()}")
            Resource.Error("Network error: ${e.message()}")
        } catch (io: IOException) {
            Log.d("API_DEBUG", "IOException: ${io.localizedMessage}")
            Resource.Error("Connection failed. Please check your internet connection.")
        } catch (e: Exception) {
            Log.d("API_DEBUG", "Unexpected exception: ${e.localizedMessage}")
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
