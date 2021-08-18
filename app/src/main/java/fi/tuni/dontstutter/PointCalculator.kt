package fi.tuni.dontstutter

/**
 * @author Juliana Pääkkönen
 * @version 2021.0818
 * @since 1.4.31
 */

/**
 * Point system for One minute mode and Relax mode.
 *
 * @param[word] guessed word
 * @return points for guessed word
 */

fun pointCalculator(word: String): Int {
    var points = 0
    for(i in word) {
        when(i) {
            'e','a','i','o','n','r','t','l','s','u' -> points += 1
            'd','g' -> points += 2
            'b','c','m','p' -> points += 3
            'f','h','v','w','y' -> points += 4
            'k' -> points += 5
            'j','x' -> points += 6
            'q','z' -> points += 7
        }
    }
    return points
}