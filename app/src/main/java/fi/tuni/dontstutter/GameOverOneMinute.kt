package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Juliana Pääkkönen
 * @version 2021.0825
 * @since 1.4.31
 */

/**
 * Activity displayed when the game is over (One Minute mode).
 *
 * @property[liveScoreView] View to display how many lives were left
 * @property[wordScoreView] View to display how many words were found
 * @property[finalScoreView] View to display final score
 * @property[newHighView] View to display current highscore or if new highscore was achieved
 * @property[liveScore] how many lives were left
 * @property[wordScore] how many words were found
 * @property[finalScore] final score
 * @property[currProfInd] index of currently active profile
 * @property[finalLiveScore] remaining hearts as score
 * @propery[currentProfile] currently active profile
 * @property[newHighscore] highscore to be saved
 */
class GameOverOneMinute : AppCompatActivity() {
    private lateinit var liveScoreView: TextView
    private lateinit var wordScoreView: TextView
    private lateinit var finalScoreView: TextView
    private lateinit var newHighView: TextView
    private var liveScore = 0
    private var wordScore = 0
    private var finalScore = 0
    private var currProfInd = 0
    private var finalLiveScore = 0
    private lateinit var currentProfile: PlayerProfile
    private lateinit var newHighscore: HighscoreProfile

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_over_one_minute)
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
            wordScore = intent.extras!!.getInt("wordScore")
            liveScore = intent.extras!!.getInt("liveScore")
            countLiveScore(liveScore)
            wordScoreView.text = "Your score: " + wordScore.toString()
            liveScoreView.text = "Extra score: " + finalLiveScore.toString()
            finalScore = wordScore + finalLiveScore
            finalScoreView.text = "Final score: " + finalScore
            this.loadHighscore("highscoreOne", ::compareHighscore)
        }
    }

    /**
     * Converts remaining hearts to score.
     *
     * @param[livesLeft] lives left at the end of the game
     */
    private fun countLiveScore(livesLeft: Int) {
        when(livesLeft) {
            0 -> finalLiveScore += 0
            1 -> finalLiveScore += 10
            2 -> finalLiveScore += 20
            3 -> finalLiveScore += 30
        }
    }

    /**
     * Checks if a new highscore is found.
     *
     * @param[prevHighscore] current highscore, loaded from preferences
     */
    private fun compareHighscore(prevHighscore: MutableList<HighscoreProfile>) {
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
                this.saveHighscore("highscoreOne", newHighscore)
            }
        } else {
            if (finalScore > 0) {
                this.newHighView.text = "NEW HIGHSCORE!"
                newHighscore = HighscoreProfile(finalScore, currentProfile.name, currentProfile.img)
                newHighscore.setDate()
                this.saveHighscore("highscoreOne", newHighscore)
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
        startActivity(Intent(this, GameOneMinute::class.java))
    }

    /**
     * Opens main menu.
     *
     * Called when "main menu" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun mainClicked(button: View) {
        startActivity(Intent(this, MenuOneMinute::class.java))
    }
}
