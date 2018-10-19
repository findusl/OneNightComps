package lehrbaum.de.onenightcomps.model

data class Composition (
    val id: Int = -1,
    val name: String = "",
    val description: String = "",
    /** The role count should always equal roles.size.
     * It is just faster on server side for database searches to have it like this.*/
    val roleCount: Int = 0,
    val difficultyLevel: Int = 1,
    val roles: Array<GameRole> = arrayOf()
)