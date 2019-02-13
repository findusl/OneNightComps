package lehrbaum.de.onenightcomps.dataaccess

import java.lang.RuntimeException

open class DataAccessException(message: String, cause: Throwable? = null)
	: RuntimeException(message, cause)

open class NetworkUnavailableException(cause: Throwable? = null)
	: DataAccessException("Network connection unavailable.", cause)

open class InternalServerErrorException(errorCode: Int, cause: Throwable? = null)
	: DataAccessException("Internal server error: $errorCode", cause)

open class ResourceNotFoundException(resourcePath: String, cause: Throwable? = null)
	: DataAccessException("Resource not found: $resourcePath", cause)

open class OperationRejectedException(message: String, cause: Throwable? = null)
	: DataAccessException(message, cause)

open class DuplicateEntryException
	: OperationRejectedException("An unique constrained in the database was violated.")

open class NotAuthorizedException(message: String)
	: OperationRejectedException(message)

open class NotLoggedInException
	: NotAuthorizedException("No user logged in")