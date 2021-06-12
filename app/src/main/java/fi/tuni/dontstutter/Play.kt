package fi.tuni.dontstutter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Activity to start playing, view highscores or view instructions.
 */
class Play : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play)
    }

    /**
     * Starts the game.
     *
     * Is called when "one minute mode" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun playClicked(button: View) {
        startActivity(Intent(this, OneMinuteGame::class.java))
    }

    /**
     * Opens highscore screen.
     *
     * Called when "highscore" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun highscoreClicked(button: View) {
        startActivity(Intent(this, Highscore::class.java))
    }

    /**
     * Opens help page.
     *
     * Is called when "help" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun helpClicked(button: View) {
        startActivity(Intent(this, Help::class.java))
    }

    /**
     * Returns back to main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
