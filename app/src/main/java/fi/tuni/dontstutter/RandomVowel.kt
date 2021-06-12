package fi.tuni.dontstutter

import kotlin.random.Random

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Generates random vowel.
 *
 * @return a random vowel as a string
 */
fun getRandomVowel(): String {
    var vowelList = listOf("a","e","i","o","u","y")
    return vowelList.shuffled()[Random.nextInt(0, vowelList.size)]
}