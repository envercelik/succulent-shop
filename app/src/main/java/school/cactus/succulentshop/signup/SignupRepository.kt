package school.cactus.succulentshop.signup

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import school.cactus.succulentshop.api.GenericErrorResponse
import school.cactus.succulentshop.api.api
import school.cactus.succulentshop.api.signup.RegisterRequest
import school.cactus.succulentshop.signup.SignupRepository.RegisterResult.Failure

class SignupRepository {
    suspend fun sendSignupRequest(
        email: String,
        password: String,
        userName: String,
    ): RegisterResult {
        val request = RegisterRequest(email, password, userName)

        val response = try {
            api.signup(request)
        } catch (e: Exception) {
            null
        }

        return when (response?.code()) {
            null -> Failure
            200 -> RegisterResult.Success(response.body()!!.jwt)
            in 400..499 -> RegisterResult.ClientError(response.errorBody()!!.errorMessage())
            else -> RegisterResult.UnexpectedError
        }
    }

    private fun ResponseBody.errorMessage(): String {
        val errorBody = string()
        val gson: Gson = GsonBuilder().create()
        val registerErrorResponse = gson.fromJson(errorBody, GenericErrorResponse::class.java)
        return registerErrorResponse.message[0].messages[0].message
    }

    sealed class RegisterResult {
        class Success(val jwt: String) : RegisterResult()
        class ClientError(val errorMessage: String) : RegisterResult()
        object UnexpectedError : RegisterResult()
        object Failure : RegisterResult()
    }
}