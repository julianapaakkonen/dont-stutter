package fi.tuni.dontstutter

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

/**
 * @author Juliana Pääkkönen
 * @version 2021.0825
 * @since 1.4.31
 */

/**
 * Class to fetch url and parse json.
 */
class WordManager {
    /**
     * Calls getUrl and parses returned JSON.
     *
     * Afterwards calls callback function and gives list of WordInfo as an argument.
     *
     * @param[url] given url
     * @param[callback] function to be called
     */
    fun createList(url: String, callback: (MutableList<WordInfo>, Boolean) -> Unit) {
        thread {
            try {
                val json = getUrl(url)
                val objMap = ObjectMapper()
                val words = objMap.readValue(json,
                    object: TypeReference<MutableList<WordInfo>>() {})
                words.forEach {
                    it.setFrequency()
                }
                callback(words, true)
            } catch(e: Exception) {
                callback(mutableListOf(), false)
            }
        }
    }

    /**
     * Class for deserializing JSON.
     *
     * @property[word] word
     * @property[tags] metadata, in this case contains only frequency
     * @property[frequency] number of times the word occurs per million words in English text
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    data class WordInfo(var word: String? = null, var tags: Array<String>? = null,
                        var frequency: Double = 0.0) {

        /**
         * Sets frequency of the word based on information received from Datamuse API.
         */
        fun setFrequency() {
            if(this.tags != null) {
                val f = tags!![0].substring(2).toDouble()
                this.frequency = f
            }
        }
    }

    /**
     * Connects to Datamuse API with given url.
     *
     * @param[url] given url
     * @return JSON from Datamuse API
     */
    private fun getUrl(url: String): String {
        try {
            val myUrl = URL(url)
            val connection = myUrl.openConnection() as HttpURLConnection
            var json = ""
            val inputStream = connection.inputStream
            inputStream.use {
                var character = it.read()
                while (character != -1) {
                    json += character.toChar()
                    character = it.read()
                }
            }
            return json
        } catch (e: Exception) {
            return ""
        }
    }
}