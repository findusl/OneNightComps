package lehrbaum.de.onenightcomps.data_access

import lehrbaum.de.onenightcomps.model.Composition
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


internal interface RestService {
    companion object {
        fun getInstance(): RestService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://lehrbaum.de/one_night_comps/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(RestService::class.java)
        }
    }

    @GET("game_composition/read.php")
    fun getCompositions() : Call<RestResponse<Array<Composition>>>
}