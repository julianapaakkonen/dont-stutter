package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

/**
 * @author Juliana Pääkkönen
 * @version 2021.0809
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
 */

class GameRelax : AppCompatActivity() {
    lateinit var l1: Button
    lateinit var l2: Button
    lateinit var l3: Button
    lateinit var l4: Button
    lateinit var l5: Button
    lateinit var g1: Button
    lateinit var g2: Button
    lateinit var g3: Button
    lateinit var g4: Button
    lateinit var g5: Button
    lateinit var g6: Button
    lateinit var g7: Button
    lateinit var g8: Button
    lateinit var g9: Button
    lateinit var g10: Button
    lateinit var g11: Button
    lateinit var g12: Button
    lateinit var g13: Button
    lateinit var g14: Button
    lateinit var g15: Button
    lateinit var submit: Button
    lateinit var h1: ImageView
    lateinit var h2: ImageView
    lateinit var h3: ImageView
    lateinit var wordCountView: TextView
    var wordCount = 0
    val wordManager = WordManager()
    var answerString = ""
    var hearts = 3
    var lettersReady = false
    var useLetters = false
    var letterList = mutableListOf<Char>()
    var letterButtonList = mutableListOf<Button>()
    var answerButtonList = mutableListOf<Button>()
    var guessedWords = mutableListOf<String>()

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_relax)
        this.l1 = findViewById(R.id.L1)
        this.l2 = findViewById(R.id.L2)
        this.l3 = findViewById(R.id.L3)
        this.l4 = findViewById(R.id.L4)
        this.l5 = findViewById(R.id.L5)
        this.g1 = findViewById(R.id.G1)
        this.g2 = findViewById(R.id.G2)
        this.g3 = findViewById(R.id.G3)
        this.g4 = findViewById(R.id.G4)
        this.g5 = findViewById(R.id.G5)
        this.g6 = findViewById(R.id.G6)
        this.g7 = findViewById(R.id.G7)
        this.g8 = findViewById(R.id.G8)
        this.g9 = findViewById(R.id.G9)
        this.g10 = findViewById(R.id.G10)
        this.g11 = findViewById(R.id.G11)
        this.g12 = findViewById(R.id.G12)
        this.g13 = findViewById(R.id.G13)
        this.g14 = findViewById(R.id.G14)
        this.g15 = findViewById(R.id.G15)
        this.submit = findViewById(R.id.submit)
        this.h1 = findViewById(R.id.h1)
        this.h2 = findViewById(R.id.h2)
        this.h3 = findViewById(R.id.h3)
        this.wordCountView = findViewById(R.id.wordcount)
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
    fun startGame() {
        lettersReady = true
        useLetters = true
    }

    /**
     * Builds a string with one randomized vowel and four question marks and calls createList.
     */
    fun createLetters() {
        var searchWordString = "?????"
        var randVow = getRandomVowel()
        var randInd = Random.nextInt(0, searchWordString.length)
        var partOne = searchWordString.subSequence(0, randInd)
        var partTwo = searchWordString.subSequence(randInd+1, searchWordString.length)
        searchWordString = partOne.toString() + randVow + partTwo
        wordManager.createList("https://api.datamuse.com/words?sp=" +
                    searchWordString + "&md=f&max=" + 100, ::showLetters)
    }

    /**
     * Displays letters on buttons.
     *
     * @param[words] a list of words from Datamuse
     */
    fun showLetters(words: MutableList<WordManager.WordInfo>) {
        if(!words.isNullOrEmpty()) {
            while (letterList.size < 12) {
                var filteredList = words.filter { it.frequency > 5 }
                var randWord = filteredList[Random.nextInt(0, filteredList.size)].word
                if(!randWord!!.contains(" ")) {
                    var charList = randWord!!.toCharArray().toMutableList()
                    charList.forEach {
                        letterList.add(it)
                    }
                }
            }
            setText(letterList.shuffled())
            setTags()
            startGame()
        } else {
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
        var b = button as Button
        if(button.text != "") {
            letterButtonList.forEach() {
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
            answerButtonList.forEach() {
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
            var b = button as Button
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
    fun letterUsed(button: Button) {
        runOnUiThread {
            button.text = ""
        }
    }

    /**
     * Checks if answer was a proper word.
     *
     * @param[answers] wordlist from Datamuse
     */
    fun checkAnswer(answers: MutableList<WordManager.WordInfo>) {
        if (!answers.isNullOrEmpty()) {
            var answerList = answers.filter { it.frequency >= 1.0 }
            if (answerString.toLowerCase() == answerList[0].word.toString()) {
                wordCount += pointCalculator(answerString.toLowerCase())
                runOnUiThread {
                    this.wordCountView.text = wordCount.toString()
                }
            } else {
                loseHeart()
            }
        } else if (answers.isEmpty()) {
            loseHeart()
        }
        resetAnswerButtons()
    }

    /**
     * Resets everything in game.
     */
    fun resetGame() {
        resetLetterButtons()
        resetAnswerButtons()
        wordCount = 0
        hearts = 3
        wordCountView.text = wordCount.toString()
        letterList.clear()
        guessedWords.clear()
    }

    /**
     * Resets keyboard buttons to "" and sets letterReady false.
     */
    fun resetLetterButtons() {
        letterButtonList.forEach() {
            runOnUiThread {
                it.text = ""
            }
        }
        lettersReady = false
    }

    /**
     * Returns letter from answerButton back to its place on keyboard.
     */
    fun resetAnswerButtons() {
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
    fun createAnswerButtonsList() {
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
            if(hearts == 3) {
                hearts--
                h3.setImageResource(R.drawable.heartempty)
            } else if(hearts == 2) {
                hearts--
                h2.setImageResource(R.drawable.heartempty)
            } else if(hearts == 1) {
                hearts--
                h1.setImageResource(R.drawable.heartempty)
                gameOver()
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
    fun setTags() {
        var x = 0
        for (i in letterButtonList) {
            i.tag = x
            x++
        }
    }

    /**
     * Sets letters to keyboard buttons.
     *
     * @param[letterListShuffled] list of letters
     */
    fun setText(letterListShuffled: List<Char>) {
        var x = 0
        for (i in letterButtonList) {
            runOnUiThread {
                i.text = letterListShuffled[x].toString()
                x++
            }
        }
    }
}

