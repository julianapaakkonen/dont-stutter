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
 * Activity to choose game mode.
 */
class MenuStart : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_start)
    }

    /**
     * Opens One Minute mode menu.
     *
     * Is called when "one minute mode" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun playOneMinuteClicked(button: View) {
        startActivity(Intent(this, MenuOneMinute::class.java))
    }

    /**
     * Opens Relax mode menu.
     *
     * Is called when "relax mode" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun playRelaxModeClicked(button: View) {
        startActivity(Intent(this, MenuRelax::class.java))
    }

    /**
     * Returns back to main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MenuMain::class.java))
    }
}
