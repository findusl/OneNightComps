package lehrbaum.de.onenightcomps.dataaccess

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import lehrbaum.de.onenightcomps.model.Composition
import lehrbaum.de.onenightcomps.model.GameRole
import lehrbaum.de.onenightcomps.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.net.UnknownHostException

private const val TAG = "RestService"

internal interface RestService {

	@GET("game_composition/read.php")
	fun getCompositions(): Call<RestResponse<Array<Composition>>>

	/**
	 * @return the id for the new composition
	 */
	@POST("game_composition/create.php")
	fun createComposition(@Body comp: Composition): Call<RestResponse<Int>>

	/**
	 * You need to set the UserRepository.currentUser for this.
	 */
	@GET("user/login.php")
	fun tryLoginCurrentUser(): Call<RestResponse<Int>>

	@POST("user/registerAsync.php")
	fun register(@Body user: User): Call<RestResponse<Boolean>>

	@GET("game_role/read.php")
	fun getRoles(): Call<RestResponse<Array<GameRole>>>

}

/**
 * I am to stupid to use correct http error codes on php side so I use my own version
 */
internal data class RestResponse<ResultType>(
	val errorMessage: String = "",
	val result: ResultType? = null
)

/**
 * Converts retrofit callbacks to Deferred
 */
internal fun <ResultType> CompletableDeferred<ResultType>.generateRetrofitCallback()
		: Callback<RestResponse<ResultType>> {
	return CallbackImpl(this)
}

private class CallbackImpl<ResultType>(val deferred: CompletableDeferred<ResultType>) :
	Callback<RestResponse<ResultType>> {
	override fun onFailure(call: Call<RestResponse<ResultType>>, t: Throwable) {
		val newT: Throwable = if (t is UnknownHostException)
			NetworkUnavailableException(t)
		else
			DataAccessException("Unknown exception occurred", t)
		deferred.completeExceptionally(newT)
	}

	override fun onResponse(
		call: Call<RestResponse<ResultType>>,
		response: Response<RestResponse<ResultType>>
	) {
		val body = response.body()
		if (response.isSuccessful && body != null) {
			if (body.errorMessage.isNotEmpty()) {
				val throwable = handleErrorMessage(body.errorMessage)
				if (throwable != null)
					deferred.completeExceptionally(throwable)
			}
			//logically would be else if, but for testing
			//I sometimes use the error message for information
			if (body.result != null) {
				deferred.complete(body.result)
			}
		} else {
			val t = when (response.code()) {
				404 -> ResourceNotFoundException(call.request().url().encodedPath())
				else -> DataAccessException("Got error code: " + response.code())
			}
			deferred.completeExceptionally(t)
		}
	}

	private fun handleErrorMessage(errorMessage: String): Throwable? {
		Log.e(TAG, "Error message: $errorMessage")
		try {
			val errorCode = Integer.parseInt(
				errorMessage.substring(0, errorMessage.indexOf(' '))
			)
			return if (errorCode == 1062)
				DuplicateEntryException()
			else
				InternalServerErrorException(errorCode)
		} catch (e: Exception) {
			Log.w(TAG, "Invalid error message, couldn't parse errorCode")
		}
		return null
	}
}