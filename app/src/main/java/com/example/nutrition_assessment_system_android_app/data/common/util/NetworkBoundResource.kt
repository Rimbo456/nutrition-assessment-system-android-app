package com.example.nutrition_assessment_system_android_app.data.common.util

import com.example.nutrition_assessment_system_android_app.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

abstract class NetworkBoundResource<ResultType, RequestType> {
    fun asFlow() = flow<Resource<ResultType>> {
        val dbValue = loadFromDb().firstOrNull()

        emit(Resource.Loading(dbValue))
        if (shouldFetch(dbValue)) {
            try {
                val apiResponse = createCall()
                saveCallResult(apiResponse)
                emitAll(loadFromDb().map { Resource.Success(it) })
            } catch (throwable: Throwable) {
                emit(Resource.Error(throwable.message ?: "Unknown error", dbValue))
            }
        } else {
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }

    protected abstract fun loadFromDb(): Flow<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): RequestType
    protected abstract suspend fun saveCallResult(data: RequestType)
}