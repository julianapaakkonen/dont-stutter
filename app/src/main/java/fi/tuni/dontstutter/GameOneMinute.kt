package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * @author Juliana Pääkkönen
 * @version 2021.0825
 * @since 1.4.31
 */

/**
 * Activity: Game where player has 1 minute to guess as many words as they can.
 *
 * @property[l1] first letter space (empty)
 * @property[l2] second letter space
 * @property[l3] third letter space (empty)
 * @property[l4] fourth letter space (empty)
 * @property[l5] fifth letter space
 * @property[submit] button to guess a word
 * @property[h1] first heart
 * @property[h2] second heart
 * @property[h3] third heart
 * @property[wordCountView] TextView for displaying current amount of guessed words
 * @property[timerView] TextView for displaying the timer
 * @property[finalSeconds] amount of seconds in the beginning
 * @property[wordList] list of words (objects) from Datamuse (contains words and their frequency)
 * @property[wordManager] object to fetch words from Datamuse
 * @property[searchWordString] string that contains parameters for query for wordlist
 * @property[searchWordMax] maximum amount of words (objects) for query for wordlist
 * @property[answerString] string that contains the word the player guessed
 * @property[searchAnswerMax] maximum amount of words (objects) for query for checking answer
 * @property[wordCount] correct guesses
 * @property[hearts] "lives"
 * @property[runTimer] timer for the game
 * @property[seconds] how many seconds there are to begin with
 * @property[firstRound] boolean for starting the timer only on first round
 * @property[wordReady] true if there is a new word to guess, false if not
 * @property[guessedWords] list of already guessed words
 * @property[usedWords] stores already used words so they won't appear again
 * @property[usedSearchWords] stores already used search words to ensure variation on wordlists
 */
class GameOneMinute : AppCompatActivity() {
    private lateinit var l1: Button
    private lateinit var l2: Button
    private lateinit var l3: Button
    private lateinit var l4: Button
    private lateinit var l5: Button
    private lateinit var submit: Button
    private lateinit var h1: ImageView
    private lateinit var h2: ImageView
    private lateinit var h3: ImageView
    private lateinit var wordCountView: TextView
    private lateinit var timerView: TextView
    private val finalSeconds = 60
    private var wordList = mutableListOf<WordManager.WordInfo>()
    private val wordManager = WordManager()
    private var searchWordString = ""
    private var searchWordMax = "100"
    private var answerString = ""
    private var searchAnswerMax = "1"
    private var wordCount = 0
    private var hearts = 3
    private var runTimer = false
    private var seconds = finalSeconds
    private var firstRound = true
    private var wordReady = false
    private var guessedWords = mutableListOf<String>()
    private var usedWords = mutableListOf<String>()
    private var usedSearchWords = mutableListOf<String>()

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_one_minute)
        l1 = findViewById(R.id.L1)
        l2 = findViewById(R.id.L2)
        l3 = findViewById(R.id.L3)
        l4 = findViewById(R.id.L4)
        l5 = findViewById(R.id.L5)
        submit = findViewById(R.id.submit)
        h1 = findViewById(R.id.h1)
        h2 = findViewById(R.id.h2)
        h3 = findViewById(R.id.h3)
        wordCountView = findViewById(R.id.wordcount)
        timerView = findViewById(R.id.timer)
    }

    /**
     * Calls super class, resets everything and starts the game.
     */
    override fun onResume() {
        super.onResume()
        resetAll()
        firstRound = true
        seconds = finalSeconds
        newWord()
    }

    /**
     * When app is on pause, timer stops
     */
    override fun onPause() {
        super.onPause()
        runTimer = false
    }

    /**
     * Builds a string with one randomized vowel and four question marks and calls createList.
     *
     * Random vowel gives more variety to word lists and decreases the possibility of repetition.
     */
    private fun newWord() {
        while(true) {
            searchWordString = "?????"
            val randVow = getRandomVowel()
            val randInd = Random.nextInt(0, searchWordString.length)
            val partOne = searchWordString.subSequence(0, randInd)
            val partTwo = searchWordString.subSequence(randInd+1, searchWordString.length)
            searchWordString = partOne.toString() + randVow + partTwo
            searchWordString = partOne.toString() + randVow + partTwo
            if(!usedSearchWords.contains(searchWordString)) {
                usedSearchWords.add(searchWordString)
                wordManager.createList("https://api.datamuse.com/words?sp=" +
                        searchWordString + "&md=f&max=" + searchWordMax, ::pickWord)
                break
            }
        }
    }

    /**
     * Picks a random word from word list and displays letters.
     *
     * Is called from WordManager.
     *
     * @param[wordList] a list of words (objects) from Datamuse
     * @param[bool] is false if Datamuse did not respond, true if it did
     */
    private fun pickWord(wordList: MutableList<WordManager.WordInfo>, bool: Boolean) {
        val filteredList = wordList.filter {
            it.frequency > 5
        }
        if(!filteredList.isNullOrEmpty() && bool) {
            if(firstRound) {
                firstRound = false
                runTimer = true
                timer()
            }
            while(!wordReady) {
                val randWord = filteredList[Random.nextInt(0, filteredList.size)]
                if (randWord.word != null && !usedWords.contains(randWord.word) &&
                        !randWord.word!!.contains(" ")) {
                    usedWords.add(randWord.word!!)
                    runOnUiThread {
                        l2.text = randWord.word!![1].toString()
                        l5.text = randWord.word!![4].toString()
                    }
                    wordReady = true
                }
            }
        } else if(!bool) {
            runOnUiThread {
                Toast.makeText(this, "Unable to connect to Datamuse",
                            Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MenuOneMinute::class.java))
            }
        }
    }

    /**
     * Checks if player's answer is a proper word by comparing it to a wordlist from Datamuse.
     *
     * Answer list has to be filtered by frequency because almost anything is recognized as a word
     * by Datamuse API. Function is called from WordManager.
     *
     * @param[answers] a list containing word(s) from Datamuse
     * @param[bool] is false if Datamuse did not respond, true if it did
     */
    private fun checkAnswer(answers: MutableList<WordManager.WordInfo>, bool: Boolean) {
        val filteredList = answers.filter {
            it.frequency >= 1.0
        }
        if(!filteredList.isNullOrEmpty() && bool) {
            if (answerString.toLowerCase() == filteredList[0].word.toString()) {
                wordCount += pointCalculator(answerString.toLowerCase())
                guessedWords.add(answerString)
                runOnUiThread {
                    wordCountView.text = wordCount.toString()
                }
                resetWords()
                newWord()
            } else {
                loseHeart()
                resetWords()
                newWord()
            }
        } else if (filteredList.isNullOrEmpty() && bool) {
            loseHeart()
            resetWords()
            newWord()
        } else if (!bool) {
            runOnUiThread {
                Toast.makeText(this, "Unable to connect to Datamuse",
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MenuOneMinute::class.java))
            }
        }
    }

    /**
     * Goes back to Main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MenuOneMinute::class.java))
        runTimer = false
    }

    /**
     * Skips a word and fetches a new one, also decreases lives by one.
     *
     * Called when "skip" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun skipClicked(button: View) {
        if(wordReady) {
            wordReady = false
            loseHeart()
            resetWords()
            newWord()
        }
    }

    /**
     * Builds a string from letters on the screen (player's guesses + preset letters) and
     * makes a query to Datamuse to get wordlist to check if it's a proper word.
     *
     * Called when "guess" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun submitClicked(button: View) {
        if(wordReady) {
            wordReady = false

            if(l1.text != "") {
                answerString += l1.text
            } else {
                answerString += "#"
            }
            answerString += l2.text
            if(l3.text != "") {
                answerString += l3.text
            } else {
                answerString += "#"
            }
            if(l4.text != "") {
                answerString += l4.text
            } else {
                answerString += "#"
            }
            answerString += l5.text

            searchAnswerMax = "10"

            if(!guessedWords.contains(answerString)) {
                wordManager.createList(
                    "https://api.datamuse.com/words?sp=" +
                            answerString + "&md=f&max=" + searchAnswerMax, ::checkAnswer)
            } else {
                runOnUiThread {
                    Toast.makeText(
                        this, "You already guessed that", Toast.LENGTH_SHORT).show()
                    resetWords()
                    newWord()
                }
            }
        }
    }

    /**
     * If pressed button contains a letter, resets it back to "".
     *
     * Called when either l1, l3 or l4 is pressed.
     *
     * @param[button] button that calls the function
     */
    fun emptyClicked(button: View) {
        val b = button as Button
        runOnUiThread {
            b.text = ""
        }
    }

    /**
     * Inserts pressed letter to first empty space.
     *
     * Called when a letter is pressed.
     *
     * @param[button] button that calls the function
     */
    fun letterClicked(button: View) {
        val b = button as Button
        when {
            l1.text == "" -> {
                l1.text = b.text
                return
            }
            l3.text == "" -> {
                l3.text = b.text
                return
            }
            l4.text == "" -> {
                l4.text = b.text
                return
            }
        }
    }

    /**
     * Decreases hearts (lives) by one.
     *
     * Opens GameOver activity if all lives are lost.
     */
    private fun loseHeart() {
        runOnUiThread {
            when (hearts) {
                3 -> {
                    hearts--
                    h3.setImageResource(R.drawable.heartempty)
                }
                2 -> {
                    hearts--
                    h2.setImageResource(R.drawable.heartempty)
                }
                1 -> {
                    hearts--
                    h1.setImageResource(R.drawable.heartempty)
                    runTimer = false
                    gameOver()
                }
            }
        }
    }

    /**
     * Opens GameOver activity
     *
     * Called when all lives are lost or time is up.
     */
    private fun gameOver() {
        val intent = Intent(this, GameOverOneMinute::class.java)
        intent.putExtra("wordScore", wordCount)
        intent.putExtra("liveScore", hearts)
        startActivity(intent)
    }

    /**
     * Resets words and empties letter spaces after every guess.
     */
    private fun resetWords() {
        runOnUiThread {
            answerString = ""
            l1.text = ""
            l2.text = ""
            l3.text = ""
            l4.text = ""
            l5.text = ""
        }
    }

    /**
     * Calls resetWords and resets everything else as well.
     */
    private fun resetAll() {
        runOnUiThread {
            resetWords()
            wordReady = false
            seconds = finalSeconds
            wordCount = 0
            wordCountView.text = wordCount.toString()
            timerView.text = seconds.toString()
            hearts = 3
            usedWords.clear()
            usedSearchWords.clear()
            guessedWords.clear()
        }
    }

    /**
     * Timer for the game
     */
    private fun timer() {
        thread {
            while(runTimer) {
                seconds--
                runOnUiThread {
                    timerView.text = seconds.toString()
                }
                if(seconds <= 0) {
                    runTimer = false
                    gameOver()
                }
                Thread.sleep(1000)
            }
        }
    }
}
