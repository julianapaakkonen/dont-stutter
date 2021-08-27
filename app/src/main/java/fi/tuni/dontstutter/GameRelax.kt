package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

/**
 * @author Juliana Pääkkönen
 * @version 2021.0825
 * @since 1.4.31
 */

/**
 * Activity: Game where the player tries to form words from given letters.
 *
 * @property[l1] first answerButton (also referred to as "empty button")
 * @property[l2] second answerButton (also referred to as "empty button")
 * @property[l3] third answerButton (also referred to as "empty button")
 * @property[l4] fourth answerButton (also referred to as "empty button")
 * @property[l5] fifth answerButton (also referred to as "empty button")
 * @property[g1] "keyboard" button
 * @property[g2] "keyboard" button
 * @property[g3] "keyboard" button
 * @property[g4] "keyboard" button
 * @property[g5] "keyboard" button
 * @property[g6] "keyboard" button
 * @property[g7] "keyboard" button
 * @property[g8] "keyboard" button
 * @property[g9] "keyboard" button
 * @property[g10] "keyboard" button
 * @property[g11] "keyboard" button
 * @property[g12] "keyboard" button
 * @property[g13] "keyboard" button
 * @property[g14] "keyboard" button
 * @property[g15] "keyboard" button
 * @property[submit] button to guess a word
 * @property[h1] first heart
 * @property[h2] second heart
 * @property[h3] third heart
 * @property[wordCountView] TextView for displaying current amount of guessed words
 * @property[wordCount] correct guesses
 * @property[wordManager] object to fetch words from Datamuse
 * @property[answerString] string that contains the word the player guessed
 * @property[hearts] "lives"
 * @property[lettersReady] boolean for when "keyboard" is ready (letters are set)
 * @property[useLetters] boolean for when letters can be pressed (prevents crashing)
 * @property[letterList] list for letters used on keyboard
 * @property[letterButtonList] list for keyboard buttons
 * @property[answerButtonList] list for answerButtons
 * @property[guessedWords] list of already guessed words
 * @propery[usedWords] stores already used words so they won't appear again
 */

class GameRelax : AppCompatActivity() {
    private lateinit var l1: Button
    private lateinit var l2: Button
    private lateinit var l3: Button
    private lateinit var l4: Button
    private lateinit var l5: Button
    private lateinit var g1: Button
    private lateinit var g2: Button
    private lateinit var g3: Button
    private lateinit var g4: Button
    private lateinit var g5: Button
    private lateinit var g6: Button
    private lateinit var g7: Button
    private lateinit var g8: Button
    private lateinit var g9: Button
    private lateinit var g10: Button
    private lateinit var g11: Button
    private lateinit var g12: Button
    private lateinit var g13: Button
    private lateinit var g14: Button
    private lateinit var g15: Button
    private lateinit var submit: Button
    private lateinit var h1: ImageView
    private lateinit var h2: ImageView
    private lateinit var h3: ImageView
    private lateinit var wordCountView: TextView
    private var wordCount = 0
    private val wordManager = WordManager()
    private var answerString = ""
    private var hearts = 3
    private var lettersReady = false
    private var useLetters = false
    private var letterList = mutableListOf<Char>()
    private var letterButtonList = mutableListOf<Button>()
    private var answerButtonList = mutableListOf<Button>()
    private var guessedWords = mutableListOf<String>()
    private var usedWords = mutableListOf<String>()

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_relax)
        l1 = findViewById(R.id.L1)
        l2 = findViewById(R.id.L2)
        l3 = findViewById(R.id.L3)
        l4 = findViewById(R.id.L4)
        l5 = findViewById(R.id.L5)
        g1 = findViewById(R.id.G1)
        g2 = findViewById(R.id.G2)
        g3 = findViewById(R.id.G3)
        g4 = findViewById(R.id.G4)
        g5 = findViewById(R.id.G5)
        g6 = findViewById(R.id.G6)
        g7 = findViewById(R.id.G7)
        g8 = findViewById(R.id.G8)
        g9 = findViewById(R.id.G9)
        g10 = findViewById(R.id.G10)
        g11 = findViewById(R.id.G11)
        g12 = findViewById(R.id.G12)
        g13 = findViewById(R.id.G13)
        g14 = findViewById(R.id.G14)
        g15 = findViewById(R.id.G15)
        submit = findViewById(R.id.submit)
        h1 = findViewById(R.id.h1)
        h2 = findViewById(R.id.h2)
        h3 = findViewById(R.id.h3)
        wordCountView = findViewById(R.id.wordcount)
        letterButtonList.add(g1)
        letterButtonList.add(g2)
        letterButtonList.add(g3)
        letterButtonList.add(g4)
        letterButtonList.add(g5)
        letterButtonList.add(g6)
        letterButtonList.add(g7)
        letterButtonList.add(g8)
        letterButtonList.add(g9)
        letterButtonList.add(g10)
        letterButtonList.add(g11)
        letterButtonList.add(g12)
        letterButtonList.add(g13)
        letterButtonList.add(g14)
        letterButtonList.add(g15)
        createAnswerButtonsList()
    }

    /**
     * Calls super class, creates letters for the game.
     */
    override fun onResume() {
        super.onResume()
        resetGame()
        createLetters()
    }

    /**
     * Sets booleans to allow pressing buttons.
     *
     * When the letters have been created, guess button can be pressed.
     *
     */
    private fun startGame() {
        lettersReady = true
        useLetters = true
    }

    /**
     * Builds a string with one randomized vowel and four question marks and calls createList.
     */
    private fun createLetters() {
        var searchWordString = "?????"
        val randVow = getRandomVowel()
        val randInd = Random.nextInt(0, searchWordString.length)
        val partOne = searchWordString.subSequence(0, randInd)
        val partTwo = searchWordString.subSequence(randInd+1, searchWordString.length)
        searchWordString = partOne.toString() + randVow + partTwo
        wordManager.createList("https://api.datamuse.com/words?sp=" +
                    searchWordString + "&md=f&max=" + 100, ::showLetters)
    }

    /**
     * Displays letters on buttons.
     *
     * @param[words] a list of words from Datamuse
     * @param[bool] is false if Datamuse did not respond, true if it did
     */
    private fun showLetters(words: MutableList<WordManager.WordInfo>, bool: Boolean) {
        val filteredList = words.filter {
            it.frequency > 5
        }
        if(!filteredList.isNullOrEmpty() && bool) {
            while (letterList.size < 12) {
                val randWord = filteredList[Random.nextInt(0, filteredList.size)].word
                if(!randWord!!.contains(" ") && !usedWords.contains(randWord)) {
                    usedWords.add(randWord)
                    val charList = randWord.toCharArray().toMutableList()
                    charList.forEach {
                        letterList.add(it)
                    }
                }
            }
            if(!letterList.contains('s')) {
                letterList[Random.nextInt(0, letterList.size)] = 's'
            }
            setText(letterList.shuffled())
            setTags()
            startGame()
        } else if(!bool) {
            runOnUiThread {
                Toast.makeText(this, "Unable to connect to Datamuse",
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MenuRelax::class.java))
            }
        }
    }

    /**
     * Returns back to menu.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MenuRelax::class.java))
    }

    /**
     * Quits the game and calls GameOver function.
     *
     * @param[button] button that calls the function
     */
    fun quitClicked(button: View) {
        gameOver()
    }

    /**
     * If pressed button contains letter, resets it back to "".
     *
     * Called when one of answerButtons is pressed
     *
     * @param[button] button that calls the function
     */
    fun emptyClicked(button: View) {
        val b = button as Button
        if(button.text != "") {
            letterButtonList.forEach {
                if (it.tag.toString() == b.tag.toString()) {
                    runOnUiThread {
                        it.text = b.text
                    }
                    b.tag = ""
                }
            }
            runOnUiThread {
                b.text = ""
            }
        }
    }

    /**
     * Forms an answer from pressed letters and connects to Datamuse to check the word.
     *
     * Is called when "guess" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun submitClicked(button: View) {
        guessedWords.add(answerString)
        if(lettersReady && useLetters) {
            useLetters = false
            answerString = ""
            answerButtonList.forEach {
                if (it.text != "") {
                    answerString += it.text
                } else {
                    answerString += "#"
                }
            }
            if(!guessedWords.contains(answerString)) {
                wordManager.createList(
                    "https://api.datamuse.com/words?sp=" +
                            answerString + "&md=f&max=" + 5, ::checkAnswer)
            } else {
                runOnUiThread {
                    Toast.makeText(
                        this, "You already guessed that", Toast.LENGTH_SHORT).show()
                    resetAnswerButtons()
                }
            }
        }
    }

    /**
     * Adds pressed letter to first free answerButton (empty button).
     *
     * Adds a tag as well.
     * Tags are used to return letter from answerButton to its place on keyboard.
     * Calls function letterUsed.
     *
     * @param[button] button that calls the function
     */
    fun letterClicked(button: View) {
        if(useLetters) {
            val b = button as Button
            for (i in answerButtonList) {
                if (i.text == "") {
                    runOnUiThread {
                        i.text = b.text
                    }
                    i.tag = b.tag
                    letterUsed(b)
                    return
                }
            }
        }
    }

    /**
     * Removes used letter from "keyboard".
     *
     * @param[button] function that calls the button
     */
    private fun letterUsed(button: Button) {
        runOnUiThread {
            button.text = ""
        }
    }

    /**
     * Checks if answer was a proper word.
     *
     * @param[answers] wordlist from Datamuse
     * @param[bool] is false if Datamuse did not respond, true if it did
     */
    private fun checkAnswer(answers: MutableList<WordManager.WordInfo>, bool: Boolean) {
        val filteredList = answers.filter {
            it.frequency >= 1.0
        }

        if (!filteredList.isNullOrEmpty() && bool) {
            if (answerString.toLowerCase() == filteredList[0].word.toString()) {
                wordCount += pointCalculator(answerString.toLowerCase())
                runOnUiThread {
                    wordCountView.text = wordCount.toString()
                }
            } else {
                loseHeart()
            }
        } else if (filteredList.isNullOrEmpty() && bool) {
            loseHeart()
        } else if (!bool) {
            runOnUiThread {
                Toast.makeText(this, "Unable to connect to Datamuse",
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MenuRelax::class.java))
            }
        }
        resetAnswerButtons()
    }

    /**
     * Resets everything in game.
     */
    private fun resetGame() {
        resetLetterButtons()
        resetAnswerButtons()
        wordCount = 0
        hearts = 3
        wordCountView.text = wordCount.toString()
        letterList.clear()
        guessedWords.clear()
        usedWords.clear()
    }

    /**
     * Resets keyboard buttons to "" and sets letterReady false.
     */
    private fun resetLetterButtons() {
        letterButtonList.forEach {
            runOnUiThread {
                it.text = ""
            }
        }
        lettersReady = false
    }

    /**
     * Returns letter from answerButton back to its place on keyboard.
     */
    private fun resetAnswerButtons() {
        for (i in answerButtonList) {
            if(i.text != "") {
                for (j in letterButtonList) {
                    if(i.tag == j.tag) {
                        runOnUiThread {
                            j.text = i.text
                            i.text = ""
                        }
                    }
                }
            }
        }
        answerButtonList.clear()
        createAnswerButtonsList()
        useLetters = true
    }

    /**
     * Adds answerButtons to a list.
     */
    private fun createAnswerButtonsList() {
        answerButtonList.add(l1)
        answerButtonList.add(l2)
        answerButtonList.add(l3)
        answerButtonList.add(l4)
        answerButtonList.add(l5)
    }

    /**
     * Decreases the amount of hearts (lives).
     *
     * When all hearts are lost, function gameOver is called.
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
                    gameOver()
                }
            }
        }
    }

    /**
     * Opens GameOver activity.
     *
     * Is called when all hearts are lost or "quit" is pressed.
     */
    private fun gameOver() {
        val intent = Intent(this, GameOverRelaxMode::class.java)
        intent.putExtra("wordScore", wordCount)
        startActivity(intent)
    }

    /**
     * Sets tags to buttons on keyboard.
     *
     * Tags are used to return a letter back to its place on keyboard.
     */
    private fun setTags() {
        for ((x, i) in letterButtonList.withIndex()) {
            i.tag = x
        }
    }

    /**
     * Sets letters to keyboard buttons.
     *
     * @param[letterListShuffled] list of letters
     */
    private fun setText(letterListShuffled: List<Char>) {
        var x = 0
        for (i in letterButtonList) {
            runOnUiThread {
                i.text = letterListShuffled[x].toString()
                x++
            }
        }
    }
}

