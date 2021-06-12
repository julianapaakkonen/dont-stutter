package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Activity to reset the game.
 */
class Reset : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reset)
    }

    /**
     * Resets profiles, highscore and currently active profile.
     *
     * Called when "yes" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun yesClicked(button: View) {
        this.resetPrefs("highscore")
        this.resetPrefs("profile")
        this.resetPrefs("currprof")
        Toast.makeText(this, "Game reset", Toast.LENGTH_LONG).show()
    }

    /**
     * Opens main menu.
     *
     * Called when "no" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun noClicked(button: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    /**
     * Goes back too main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
