package com.example.semi_final_exam.data

sealed interface AppResult<out T> {
    data class Success<out T>(val data: T) : AppResult<T>

    sealed interface Failure : AppResult<Nothing> {
        data object WrongLogin : Failure
        data object EmailTaken : Failure
        data class NetworkError(val message: String? = null, val throwable: Throwable? = null) : Failure
        data class UnknownError(val message: String? = null, val throwable: Throwable? = null) : Failure
    }
}
