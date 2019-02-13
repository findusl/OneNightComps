package lehrbaum.de.onenightcomps.dataaccess

import lehrbaum.de.onenightcomps.model.Composition
import lehrbaum.de.onenightcomps.model.GameRole
import lehrbaum.de.onenightcomps.model.User
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class MockRestService : RestService{

	private val compositions : MutableList<Composition> = ArrayList()
	private var highestCompositionIndex = 0

	init{
		compositions.add(Composition(highestCompositionIndex++, "some name", "some description"))
	}

    override fun getCompositions(): Call<RestResponse<Array<Composition>>> {
        return DummyCall(compositions.toTypedArray())
    }

	@Synchronized
    override fun createComposition(comp: Composition): Call<RestResponse<Int>> {
		compositions.add(comp.copy(id = highestCompositionIndex))
		return DummyCall(highestCompositionIndex++)
    }

    override fun tryLoginCurrentUser(): Call<RestResponse<Int>> {
        return DummyCall(1)
    }

    override fun register(user: User): Call<RestResponse<Boolean>> {
        TODO("not implemented")//could do user list and implement login login
    }

    override fun getRoles(): Call<RestResponse<Array<GameRole>>> {
        return DummyCall(arrayOf(GameRole(1, "role", "description")))
    }

}

internal class DummyCall<T>(result: T) : Call<RestResponse<T>> {

    private val restResponse = RestResponse(result = result)

    override fun enqueue(callback: Callback<RestResponse<T>>) {
        callback.onResponse(this, Response.success(restResponse))
    }

    override fun isExecuted(): Boolean {
        throw NotImplementedError()
    }

    override fun clone(): Call<RestResponse<T>> {
        throw NotImplementedError()
    }

    override fun isCanceled(): Boolean {
        throw NotImplementedError()
    }

    override fun cancel() {
        throw NotImplementedError()
    }

    override fun execute(): Response<RestResponse<T>> {
        throw NotImplementedError()
    }

    override fun request(): Request {
        throw NotImplementedError()
    }
}