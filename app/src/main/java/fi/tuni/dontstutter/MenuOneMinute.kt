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
 * Menu for One Minute mode.
 */

class MenuOneMinute : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_one_minute)
    }

    /**
     * Starts the game.
     *
     * Is called when "play" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun playClicked(button: View) {
        startActivity(Intent(this, GameOneMinute::class.java))
    }

    /**
     * Opens highscore for One Minute mode.
     *
     * Is called when "highscore" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun highscoreClicked(button: View) {
        startActivity(Intent(this, HighscoreOneMinute::class.java))
    }

    /**
     * Opens instructions for One Minute mode.
     *
     * Is called when "help" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun helpClicked(button: View) {
        startActivity(Intent(this, HelpOneMinute::class.java))
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