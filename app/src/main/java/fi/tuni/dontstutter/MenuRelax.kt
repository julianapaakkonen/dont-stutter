package fi.tuni.dontstutter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

/**
 * @author Juliana Pääkkönen
 * @version 2021.0823
 * @since 1.4.31
 */

/**
 * Menu for Relax mode.
 */
class MenuRelax : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_relax)
    }

    /**
     * Starts the game.
     *
     * Is called when "play" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun playClicked(button: View) {
        startActivity(Intent(this, GameRelax::class.java))
    }

    /**
     * Opens highscore for Relax mode.
     *
     * Is called when "highscore" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun highscoreClicked(button: View) {
        startActivity(Intent(this, HighscoreRelax::class.java))
    }

    /**
     * Opens instructions for Relax mode.
     *
     * Is called when "help" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun helpClicked(button: View) {
        startActivity(Intent(this, HelpRelax::class.java))
    }

    /**
     * Returns back to previous menu.
     *
     * Is called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MenuStart::class.java))
    }
}