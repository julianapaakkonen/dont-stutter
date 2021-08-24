<h1>Don't Stutter</h1>


<h2>Overview</h2>

<p><strong>Don't Stutter</strong> is a mobile game for Android where you try to guess as many words as you can. There are two modes: One Minute mode and Relax mode.</p>
<p><strong>One Minute mode</strong> was done as a project for my native mobile app development studies. In One Minute mode you have exactly 60 seconds to guess five letter words and collect points.</p>
<p><strong>Relax mode</strong> was done later as a free time project and it’s also a word game but you have no time limits!</p>


<h2>API</h2>

<p>Datamuse API</p>


<h2>Screenshots</h2>

<img src="https://user-images.githubusercontent.com/77321592/130601704-e38cd8b5-c215-4aea-a1a0-8496faa8c1ee.jpg" width="200"> <img src="https://user-images.githubusercontent.com/77321592/130601712-e10f5493-f54a-4575-ae0d-a86fe7d7e1fa.jpg" width="200"> <img src="https://user-images.githubusercontent.com/77321592/130601715-3cc1ef2b-4126-4f95-8639-01d81686a4ce.jpg" width="200"> <img src="https://user-images.githubusercontent.com/77321592/130601717-dbd33908-bf99-457e-82b2-4e600288c292.jpg" width="200">


<h2>Features</h2>

<h3>Modes</h3>

<p>One Minute mode gives you one minute (as the name suggests) to guess as many words as you possibly can. The game displays random letters and you just have to fill in other letters to form a five-letter word. If the word is a common English word, you receive points based on the difficulty of the word. If you guess nonsense or skip, one heart is lost. If three hearts are lost or the timer hits zero, the game ends.</p>
<p>Relax mode is based on the same idea as One Minute mode but this time there’s no timer. The game gives you 15 letters that you can use to form words and receive points. The game ends if you guess non-words too many times but otherwise this mode is worth its name – quite relaxing.</p>

<h3>Highscore & Profiles</h3>

<p>Up to five profiles can be created for different players on the same device. Profile contains name and photo (camera feature supports back camera / landscape only). Currently active profile can be switched in “Profiles”. Top scores are saved in both game modes.</p>


<h2>Code example</h2>

<p>PickWord is one of the most important functions in One Minute mode. It is responsible for delivering the two letters from which the player has to guess a word. PickWord is called when a list of words is received from Datamuse API. This function chooses a word that meets certain requirements and shows two letters to the player. This procedure ensures that there is always at least one common word in the English language that fits the letters displayed on screen.</p>
<p>Here is a snippet of PickWord:</p>

    if(!words.isNullOrEmpty()) {
        if(firstRound) {
            firstRound = false
            runTimer = true
            timer()
        }
  
        words.forEach {
            wordList.add(it)
        }
        
        val filterList = wordList.filter {
            it.frequency > 5
        }
        
        var wordFound = false

        while (!wordFound) {
            val randWord = filterList[Random.nextInt(0, filterList.size)]
            if (randWord.word != null && !usedWords.contains(randWord.word) &&
                !randWord.word!!.contains(" ")) {
                pickedWord = randWord.word!!
                usedWords.add(randWord.word!!)
                wordFound = true
                runOnUiThread {
                    this.l2.text = pickedWord[1].toString()
                    this.l5.text = pickedWord[4].toString()
                }
                wordReady = true
            }
        }
    }


<h2>Known bugs</h2>

<h3>General</h3>
<ul><li>Some words in Datamuse API contain numbers and special characters and those should not be used in Don't Stutter. Filtering words based on frequency should get rid of those rare occurrences.</li></ul>
<h3>One Minute mode</h3>
<ul><li>There is a rare bug where the timer keeps going even though the game has been stopped. This makes the Game Over -screen open when the time is up.</li>
<li>Datamuse API can sometimes respond very slowly and therefore new letters don’t appear. This doesn't stop timer if it has been started.</li></ul>
<h3>Relax mode</h3>
<ul><li>There are no known bugs (yet).</li></ul>


<h2>Developer / Contact</h2>

<p>Juliana Pääkkönen / julianapaakkonen@outlook.com</p>


<h2>Thanks</h2>
<p>Scrabble for providing score values for letters!</p>


