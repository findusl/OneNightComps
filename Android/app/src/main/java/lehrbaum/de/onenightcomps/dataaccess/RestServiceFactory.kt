package lehrbaum.de.onenightcomps.dataaccess

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import lehrbaum.de.onenightcomps.inject
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val baseUrl = "https://lehrbaum.de/one_night_comps/"

internal object RestServiceFactory {

	fun getRestService(): RestService {
		//return MockRestService()
		return createRestService()
	}

	private fun createRestService(): RestService {

		val client = OkHttpClient.Builder()
			.addInterceptor(BasicAuthInterceptor())
			.addLogger()
			.build()
		val retrofit = Retrofit.Builder()
			.baseUrl(baseUrl)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create(getGSON()))
			.build()
		return retrofit.create(RestService::class.java)
	}

	private fun OkHttpClient.Builder.addLogger(): OkHttpClient.Builder {
		val logger = HttpLoggingInterceptor()
		logger.level = HttpLoggingInterceptor.Level.BODY
		this.addInterceptor(logger)
		return this
	}

	private fun getGSON(): Gson {
		//need to alter this if I want null
		return GsonBuilder().create()
	}

	// TODO this is not good style, the user should be passed when calling rest methods
	// Hidden dependencies like this are a code smell
	private class BasicAuthInterceptor : Interceptor {
		val userRepository: UserRepository by inject()

		override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
			if (userRepository.currentUser == null)
				return chain.proceed(chain.request())
			val request = chain.request()
			val authenticatedRequest = request.newBuilder()
				.header("Authorization", credentialsFromCurrentUser()).build()
			return chain.proceed(authenticatedRequest)
		}

		fun credentialsFromCurrentUser(): String {
			val user = userRepository.currentUser ?: return ""
			return Credentials.basic(user.username, user.passwordBase64)
		}
	}
}