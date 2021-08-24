package fi.tuni.dontstutter

import HighscoreProfile
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView

/**
 * @author Juliana Pääkkönen
 * @version 2021.0823
 * @since 1.4.31
 */

/**
 * Activity that displays current top 5 scores for Relax mode.
 *
 * @property[highscoreView] ListView to display highscore
 * @property[adapter] custom adapter for ListView
 */

class HighscoreRelax : AppCompatActivity() {
    lateinit var highscoreView: ListView
    lateinit var adapter: HighscoreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.highscore_relax)
        this.highscoreView = findViewById(R.id.listView)
        this.loadHighscore("highscoreRelax", ::displayHighscore)
    }

    /**
     * Displays highscore in ListView.
     *
     * @param[highscore] current highscore, loaded from preferences
     */
    fun displayHighscore(highscore: MutableList<HighscoreProfile>) {
        val arraylist = ArrayList(highscore)
        adapter = HighscoreAdapter(this, arraylist)
        highscoreView.adapter = adapter
    }

    /**
     * Returns back to main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MenuRelax::class.java))
    }
}
