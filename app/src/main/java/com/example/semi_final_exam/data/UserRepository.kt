package com.example.semi_final_exam.data

import com.example.semi_final_exam.data.network.NetworkModule
import com.example.semi_final_exam.data.network.UserApiService
import com.example.semi_final_exam.data.network.dto.NewUserDto
import com.example.semi_final_exam.data.network.dto.UserDto
import com.example.semi_final_exam.data.network.dto.toDomain
import com.example.semi_final_exam.domain.model.User
import retrofit2.HttpException
import java.io.IOException

class UserRepository(
    private val api: UserApiService = NetworkModule.userApiService
) {
    private suspend fun findUsers(email: String): List<UserDto> {
        return try {
            api.findByEmail(email)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                emptyList()
            } else {
                throw e
            }
        }
    }

    private inline fun <T> safeCall(block: () -> AppResult<T>): AppResult<T> {
        return try {
            block()
        } catch (e: HttpException) {
            AppResult.Failure.NetworkError(e.message(), e)
        } catch (e: IOException) {
            AppResult.Failure.NetworkError(e.message ?: "Network error", e)
        } catch (e: Exception) {
            AppResult.Failure.UnknownError(e.message ?: "Unknown error", e)
        }
    }

    suspend fun login(email: String, password: String): AppResult<User> = safeCall {
        val trimmedEmail = email.trim()
        val users = findUsers(trimmedEmail)
        val found = users.firstOrNull {
            it.email?.equals(trimmedEmail, ignoreCase = true) == true && it.password == password
        }
        if (found == null) {
            AppResult.Failure.WrongLogin
        } else {
            AppResult.Success(found.toDomain())
        }
    }

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        birthdate: String
    ): AppResult<User> = safeCall {
        val trimmedEmail = email.trim()
        val users = findUsers(trimmedEmail)
        val isEmailTaken = users.any {
            it.email?.equals(trimmedEmail, ignoreCase = true) == true
        }
        if (isEmailTaken) {
            AppResult.Failure.EmailTaken
        } else {
            val newUser = NewUserDto(
                fullname = fullName.trim(),
                email = trimmedEmail,
                password = password,
                birthdate = birthdate.trim()
            )
            val saved = api.createUser(newUser)
            AppResult.Success(saved.toDomain())
        }
    }
}
