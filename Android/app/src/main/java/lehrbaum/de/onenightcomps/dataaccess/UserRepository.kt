package lehrbaum.de.onenightcomps.dataaccess

import android.util.Base64
import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.User
import java.nio.charset.Charset
import java.util.*

private const val TAG = "UserRepository"

class UserRepository {
	private val restService: RestService by inject()

	var currentUser: User? = null
		private set

	@Synchronized
	suspend fun login(userName: String, password: String): Boolean {
		val pwBase64 = password.toBase64()
		currentUser = User(-1, userName, pwBase64)
		val deferred = CompletableDeferred<Int>()
		restService.tryLoginCurrentUser().enqueue(deferred.generateRetrofitCallback())
		return handleLoginResponse(userName, deferred.await())
	}

	@Synchronized
	private fun handleLoginResponse(userName: String, id: Int): Boolean {
		when {
			id == -1 -> {
				currentUser = null
				return false
			}
			//make sure no other login operations happened meanwhile
			currentUser != null
					&& currentUser!!.username == userName
					&& currentUser!!.id != id -> {
				currentUser = currentUser!!.copy(id = id)
				userListeners.forEach {it(currentUser)}
				return true
			}
			else -> {
				Log.w(TAG, "Successful login response but " +
						"current user was in incorrect state.")
				return false
			}
		}
	}

	fun registerAsync(userName: String, password: String, eMail: String): Deferred<Boolean> {
		val user = User(-1, userName, password.toBase64(), eMail)
		val deferred = CompletableDeferred<Boolean>()
		restService.register(user).enqueue(deferred.generateRetrofitCallback())
		return deferred
	}

	fun logout(){
		currentUser = null
		userListeners.forEach{it(currentUser)}
	}

	private val userListeners = LinkedList<(User?) -> Unit>()

	fun addOnUserChangedListener(listener: (User?) -> Unit) {
		userListeners.add(listener)
	}
}

private fun String.toBase64(): String {
	val bytes = this.toByteArray(Charset.forName("UTF-8"))
	return Base64.encodeToString(bytes, Base64.NO_WRAP)
}