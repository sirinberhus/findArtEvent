package com.sirin.findartevent.data.repository

import android.content.SharedPreferences
import com.sirin.findartevent.data.model.ErrorsResponse
import com.sirin.findartevent.data.model.LoginRequest
import com.sirin.findartevent.data.model.RegisterRequest
import com.sirin.findartevent.data.network.ApiErrorExceptions
import com.sirin.findartevent.data.network.AuthApiService
import com.sirin.findartevent.data.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException

class AuthRepositoryImpl(
    private val retrofit: Retrofit,
    private val service: AuthApiService,
    private val prefs: SharedPreferences
) : AuthRepository {

    private  fun convertErrorBody(
        errorBody: ResponseBody?
    ): ErrorsResponse? {
        val converter: Converter<ResponseBody, ErrorsResponse> =
            retrofit.responseBodyConverter(ErrorsResponse::class.java, arrayOfNulls<Annotation>(0))
        var apiError: ErrorsResponse? = null

        try {
            apiError = converter.convert(errorBody)
        } catch (e: ApiErrorExceptions) {
            e.printStackTrace()
        }

        return apiError
    }

    override suspend fun login(
        email: String,
        password: String
    ): Result<out Any> {
        return withContext(Dispatchers.IO){
            try {
                val response = service.login(
                    request = LoginRequest(
                        email,
                        password
                    )
                )
                if (response.isSuccessful) {
                    prefs.edit()
                        .putString("token_type", response.body()?.tokenType)
                        .putString("access_token", response.body()?.accessToken)
                        .apply()

                    Result.Authorized(
                        data = getCurrentUser()
                    )

                } else {
                    val errors = convertErrorBody (
                        response.errorBody()
                    )
                    Result.ApiError(
                        data = errors
                    )
                }
            } catch (e: HttpException ) {
                Result.Unauthorized()
            } catch (e: IOException) {
                Result.ApiError(
                    data = ErrorsResponse(
                        message = "Please connect to internet"
                    )
                )
            } catch (e: Exception) {
                Result.Unauthorized()
            }
        }

    }

    override suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String
    ): Result<out Any> {
        return withContext(Dispatchers.IO){
            try {
                val response = service.register(
                    request = RegisterRequest(
                        name,
                        lastName,
                        email,
                        password
                    )
                )
                if (response.isSuccessful) {
                    prefs.edit()
                        .putString("token_type", response.body()?.tokenType)
                        .putString("access_token", response.body()?.accessToken)
                        .apply()

                    Result.Authorized(
                        data = getCurrentUser()
                    )

                } else {
                    val errors = convertErrorBody (
                        response.errorBody()
                    )
                    Result.ApiError(
                        data = errors
                    )
                }
            } catch (e: HttpException ) {
                Result.Unauthorized()
            } catch (e: IOException) {
                Result.ApiError(
                    data = ErrorsResponse(
                        message = "${e.toString()}Please connect to internet"
                    )
                )
            } catch (e: Exception) {
                Result.Unauthorized()
            }
        }
    }

    override suspend fun getCurrentUser(): Result<out Any> {

        return withContext(Dispatchers.IO){
            try {
                val token = prefs.getString("access_token", null)

                val response = service.users("bearer $token")

                if(response.isSuccessful) {
                    Result.Authorized(
                        data = response.body()
                    )
                } else {
                    val errors = convertErrorBody(
                        response.errorBody()
                    )

                    Result.ApiError(
                        data = errors
                    )
                }
            } catch (e: IOException) {
                Result.ApiError(
                    data = ErrorsResponse (
                        message = "Please connect to the internet"
                    )
                )
             } catch (e: HttpException) {
                Result.Unauthorized()
            } catch (e: Exception) {
                   Result.Unauthorized()
            }
        }

    }

    override suspend fun logout(): Result<out Any> {
       return withContext(Dispatchers.IO) {
           try {
                val token = prefs.getString("access_token", null)

               val response = service.logout("Bearer $token")

               if (response.isSuccessful) {
                   prefs.edit()
                       .putString("token_type", null)
                       .putString("access_token", null)
                       .apply()

                   Result.Unauthorized()
               } else {
                   Result.Authorized()
               }
           }catch (e: IOException) {
               Result.ApiError(
                   data = ErrorsResponse (
                       message = "Please connect to the internet"
                   )
               )
           } catch (e: HttpException) {
               Result.Unauthorized()
           } catch (e: Exception) {
               Result.Unauthorized()
           }
       }
    }
}