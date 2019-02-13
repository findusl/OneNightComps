package lehrbaum.de.onenightcomps.model

data class User (
	val id: Int,
	val username: String,
	val passwordBase64: String,
	val eMail: String? = null
)