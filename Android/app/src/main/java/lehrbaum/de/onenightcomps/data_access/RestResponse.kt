package lehrbaum.de.onenightcomps.data_access

/**
 * I am to stupid to use correct http error codes on php side so I use my own version
 */
internal data class RestResponse<ResultType> (
    val errorMessage: String = "",
    val result: ResultType? = null
)