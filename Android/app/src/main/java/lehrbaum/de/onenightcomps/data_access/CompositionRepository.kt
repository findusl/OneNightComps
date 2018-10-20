package lehrbaum.de.onenightcomps.data_access

import android.util.Log
import androidx.core.util.Consumer
import lehrbaum.de.onenightcomps.model.Composition
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CompositionRepository {
    private const val TAG = "CompositionRepository"
    private var restService: RestService = RestService.getInstance()

    fun getCompositions(callback: Consumer<Array<Composition>>, errorResolver: Resolution) {
        restService.getCompositions().enqueue(convertRetrofitCallback(callback, errorResolver))
    }

    /**
     * This method takes care of converting the retrofit callback to our style of consumer callback and error resolver
     */
    private fun <ResultType>convertRetrofitCallback(callback: Consumer<ResultType>, errorResolver: Resolution): Callback<RestResponse<ResultType>> {
        return object: Callback<RestResponse<ResultType>> {
            override fun onFailure(call: Call<RestResponse<ResultType>>, t: Throwable) {
                //TODO I assume no internet raises exception here
                errorResolver.onUnkownException(t)
            }

            override fun onResponse(
                call: Call<RestResponse<ResultType>>,
                response: Response<RestResponse<ResultType>>
            ) {
                if(response.isSuccessful) {
                    if(response.body() != null) {
                        val body = response.body()!!
                        if(!body.errorMessage.isEmpty()) {
                            TODO("Handle the server error message: " + body.errorMessage)
                        } else {
                            callback.accept(body.result)
                        }
                    } else {
                        Log.wtf(TAG, "onResponse: No body even thought request was successful. " +
                                "That is weird. Response code: " + response.code());
                        errorResolver.onRessourceNotFound()
                    }
                } else {
                    TODO("Handle the http error code: " + response.code())
                }
            }

        }
    }
}