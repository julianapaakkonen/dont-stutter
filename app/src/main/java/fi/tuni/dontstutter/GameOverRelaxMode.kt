package fi.tuni.dontstutter

import HighscoreProfile
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Juliana Pääkkönen
 * @version 2021.0823
 * @since 1.4.31
 */

/**
 * Activity displayed when game is over (Relax mode).
 *
 * @property[wordScoreView] View to display how many words were found
 * @property[finalScore] final score
 * @property[newHighView] View to display current highscore or if new highscore was achieved
 * @property[currProfInd] index of currently active profile
 * @property[currentProfile] currently active profile
 * @property[newHighscore] highscore to be saved
 */
class GameOverRelaxMode : AppCompatActivity() {
    lateinit var wordScoreView: TextView
    var finalScore = 0
    lateinit var newHighView: TextView
    var currProfInd = 0
    lateinit var currentProfile: PlayerProfile
    lateinit var newHighscore: HighscoreProfile

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over_relax)
        this.wordScoreView = findViewById(R.id.wordscore)
        this.newHighView = findViewById(R.id.newhigh)
        loadCurrentProf("currprof") {
            this.currProfInd = it
        }
        loadProfile("profile") {
            if(it.isNotEmpty()) {
                this.currentProfile = it[currProfInd]
            } else {
                this.currentProfile = PlayerProfile()
            }
        }
    }

    /**
     * Sets scores on the screen.
     */
    override fun onResume() {
        super.onResume()
        if(intent.extras != null) {
            this.finalScore = intent.extras!!.getInt("wordScore")
            this.wordScoreView.text = "Your score: " + finalScore
            this.loadHighscore("highscoreRelax", ::compareHighscore)
        }
    }

    /**
     * Checks if a new highscore is found.
     *
     * @param[prevHighscore] current highscore, loaded from preferences
     */
    fun compareHighscore(prevHighscore: MutableList<HighscoreProfile>) {
        var newHigh =  false
        if(!prevHighscore.isNullOrEmpty()) {
            var currentHigh = prevHighscore[0].score
            if(currentHigh < finalScore) {
                this.newHighView.text = "NEW HIGHSCORE!"
            } else {
                this.newHighView.text = "Current HIGHSCORE " + currentHigh
            }

            prevHighscore.forEach {
                if(it.score < finalScore) {
                    newHigh = true
                }
            }

            if(prevHighscore.size < 10 && finalScore > 0) {
                newHigh = true
            }

            if(newHigh) {
                newHighscore = HighscoreProfile(finalScore, currentProfile.name, currentProfile.img)
                newHighscore.setDate()
                this.saveHighscore("highscoreRelax", newHighscore)
            }
        } else {
            if (finalScore > 0) {
                this.newHighView.text = "NEW HIGHSCORE!"
                newHighscore = HighscoreProfile(finalScore, currentProfile.name, currentProfile.img)
                newHighscore.setDate()
                this.saveHighscore("highscoreRelax", newHighscore)
            } else {
                this.newHighView.text = "Try again"
            }
        }
    }

    /**
     * Opens OneMinute mode and starts a new game.
     *
     * Called when "play again" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun againClicked(button: View) {
        startActivity(Intent(this, GameRelax::class.java))
    }

    /**
     * Opens main menu.
     *
     * Called when "main menu" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun mainClicked(button: View) {
        startActivity(Intent(this, MenuRelax::class.java))
    }
}

