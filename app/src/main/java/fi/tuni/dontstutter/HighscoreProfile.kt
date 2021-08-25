package fi.tuni.dontstutter

import java.util.*

/**
 * @author Juliana Pääkkönen
 * @version 2021.0825
 * @since 1.4.31
 */

/**
 * Creates objects for highscore.
 *
 * @property[score] highscore
 * @property[name] player's name
 * @property[imgPath] player's profile picture
 * @property[date] current date
 */
data class HighscoreProfile(val score: Int = 0, val name: String  = "anonymous",
                            val imgPath: String? = R.drawable.defaultprofpic.toString(),
                            var date: String = "00-00-0000") {

    /**
     * Sets date for highscore.
     */
    fun setDate() {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        this.date = "$day.$month.$year"
    }

    /**
     * Formats score, name and date.
     *
     * @return formatted String containing score, name and date
     */
    override fun toString(): String {
        return "%-5s\t%-10s\t%10s".format("$score", "$name", "$date")
    }
}
