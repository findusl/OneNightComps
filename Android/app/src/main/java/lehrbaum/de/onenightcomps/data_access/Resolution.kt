package lehrbaum.de.onenightcomps.data_access

interface HttpResolution {
    fun onServerUnavailable()
    fun onInternalServerError()
    fun onRessourceNotFound()
    fun onUnkownException(t: Throwable)
}

interface NetworkConnectivityResolution {
    fun onNetworkUnavailable()
}

interface PermissionMismatchResolution {
    fun onInsufficientRights()
}

interface Resolution : HttpResolution, NetworkConnectivityResolution, PermissionMismatchResolution {
}