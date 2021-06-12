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
 * Activity where the app starts (Main menu).
 */
class MainActivity : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Starts the game.
     *
     * Is called when "play" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun playClicked(button: View) {
        startActivity(Intent(this, Play::class.java))
    }

    /**
     * Opens profile list.
     *
     * Is called when "profiles" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun profilesClicked(button: View) {
        startActivity((Intent(this, Profile::class.java)))
    }

    /**
     * Opens reset screen, where user can determine if they want to reset or not.
     *
     * Called when "reset game" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun resetClicked(button: View) {
        startActivity(Intent(this, Reset::class.java))
    }

    /**
     * Closes the app.
     *
     * Called when "quit" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun quitClicked(button: View) {
        finishAffinity()
    }
}
