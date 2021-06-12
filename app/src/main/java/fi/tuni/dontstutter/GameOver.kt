package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Activity displayed when the game is over.
 *
 * @property[liveScoreView] View to display how many lives were left
 * @property[wordScoreView] View to display how many words were found
 * @property[finalScoreView] View to display final score
 * @property[newHighView] View to display current highscore or if new highscore was achieved
 * @property[liveScore] how many lives were left
 * @property[wordScore] how many words were found
 * @property[finalScore] final score (lives + words)
 * @property[currProfInd] index of currently active profile
 * @propery[currentProfile] currently active profile
 * @property[newHighscore] highscore to be saved
 */
class GameOver : AppCompatActivity() {
    lateinit var liveScoreView: TextView
    lateinit var wordScoreView: TextView
    lateinit var finalScoreView: TextView
    lateinit var newHighView: TextView
    var liveScore = 0
    var wordScore = 0
    var finalScore = 0
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
        setContentView(R.layout.game_over)
        this.wordScoreView = findViewById(R.id.wordscore)
        this.liveScoreView = findViewById(R.id.livescore)
        this.finalScoreView = findViewById(R.id.finalScore)
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
            this.wordScore = intent.extras!!.getInt("wordScore")
            this.liveScore = intent.extras!!.getInt("liveScore")
            this.finalScore = wordScore + liveScore
            this.wordScoreView.text = "You found " + wordScore + " WORDS"
            this.liveScoreView.text = "You have " + liveScore + " HEARTS left"
            this.finalScoreView.text = "FINAL SCORE " + finalScore
            this.loadHighscore("highscore", ::compareHighscore)
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
                this.saveHighscore("highscore", newHighscore)
            }
        } else {
            if (finalScore > 0) {
                this.newHighView.text = "NEW HIGHSCORE!"
                newHighscore = HighscoreProfile(finalScore, currentProfile.name, currentProfile.img)
                newHighscore.setDate()
                this.saveHighscore("highscore", newHighscore)
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
        startActivity(Intent(this, OneMinuteGame::class.java))
    }

    /**
     * Opens main menu.
     *
     * Called when "main menu" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun mainClicked(button: View) {
        startActivity(Intent(this, Play::class.java))
    }
}

/**
 * Creates objects for highscore.
 *
 * @property[score] highscore
 * @property[name] player's name
 * @property[imgPath] player's profile picture
 * @property[date] current date
 */
data class HighscoreProfile(val score: Int = 0, val name: String  = "anonymous",
                            val imgPath: String? = R.drawable.defaultprofpic.toString(),
                            var date: String = "00-00-0000") {

    /**
     * Sets date for highscore.
     */
    fun setDate() {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH) + 1
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        this.date = "$day.$month.$year"
    }

    /**
     * Formats score, name and date.
     *
     * @return formatted String containing score, name and date
     */
    override fun toString(): String {
        return "%-4s\t%-10s\t%10s".format("$score", "$name", "$date")
    }
}
