package lehrbaum.de.onenightcomps.model

private const val amountCardsInTheCenter = 3
const val MinimumCardCount = 5

data class Composition(
	val id: Int = -1,
	val name: String = "",
	val description: String = "",
	val difficultyLevel: Int = 1,
	val roles: Array<GameRole> = arrayOf()
) {
	val playerCount: Int
		get() {
			return roles.size - amountCardsInTheCenter
		}

	override fun equals(other: Any?): Boolean {
		if (javaClass != other?.javaClass) return false

		other as Composition
		return id == other.id
	}

	override fun hashCode(): Int {
		return id
	}
}