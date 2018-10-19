package lehrbaum.de.onenightcomps.model

data class GameRole (
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    val faction: Faction? = null
)