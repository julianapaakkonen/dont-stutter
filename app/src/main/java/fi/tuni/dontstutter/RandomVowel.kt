package fi.tuni.dontstutter

import kotlin.random.Random

/**
 * @author Juliana Pääkkönen
 * @version 2021.0827
 * @since 1.4.31
 */

/**
 * Generates random vowel.
 *
 * Letter Y was excluded because it is not as common as other vowels
 * @return a random vowel as a string
 */
fun getRandomVowel(): String {
    val vowelList = listOf("a","e","i","o","u")
    return vowelList.shuffled()[Random.nextInt(0, vowelList.size)]
}
