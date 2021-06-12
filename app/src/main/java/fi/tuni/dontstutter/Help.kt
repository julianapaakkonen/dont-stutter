package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Activity to display instructions.
 */
class Help : AppCompatActivity() {

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.help_page)
    }

    /**
     * Returns back to main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, Play::class.java))
    }
}
