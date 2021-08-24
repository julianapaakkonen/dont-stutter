package fi.tuni.dontstutter

import HighscoreProfile
import android.app.Activity
import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Loads highscore from preferences.
 *
 * @param[key] key for loading
 * @param[callback] function to be called when data is done loading
 */
fun Activity.loadHighscore(key: String, callback: (MutableList<HighscoreProfile>) -> Unit) {
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE) ?: return
    val highScore = pref.getString(key, "")

    try {
        var highScoreList = ObjectMapper().readValue(highScore,
                            object : TypeReference<MutableList<HighscoreProfile>>() {})
        var listInOrder = orderList(highScoreList)
        callback(listInOrder)
    } catch(e: Exception) {
        callback(mutableListOf())
    }
}

/**
 * Saves highscore to preferences.
 *
 * Called when new highscore is achieved.
 *
 * @param[key] key for saving
 * @param[highscore] new highscore to be saved
 */
fun Activity.saveHighscore(key: String, highscore: HighscoreProfile) {
    var list = mutableListOf<HighscoreProfile>()
    this.loadHighscore(key) {
        list = it
    }

    list.add(highscore)
    var topTen = orderList(list)
    var json: String = ObjectMapper().writeValueAsString(topTen)
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE) ?: return
    with (pref.edit()) {
        putString(key, json)
        apply()
    }
}

/**
 * Loads active profile (index) from preferences.
 *
 * @param[key] key for loading
 * @param[callback] function to be called when data is done loading
 */
fun Activity.loadCurrentProf(key: String, callback: (Int) -> Unit) {
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE) ?: return
    val currProf = pref.getInt(key, 0)
    callback(currProf)
}

/**
 * Saves active profile (index) to preferences.
 *
 * @param[key] key for saving
 * @param[currProfIndex] profile index to be saved
 */
fun Activity.saveCurrentProf(key: String, currProfIndex: Int) {
    resetPrefs(key)
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE) ?: return
    with (pref.edit()) {
        putInt(key, currProfIndex)
        apply()
    }
}

/**
 * Puts highscore list in order by score and slices only the first 5 entries.
 *
 * @param[list] list to be put in order
 * @return list in order and only top 5 high scores
 */
fun orderList(list: MutableList<HighscoreProfile>): MutableList<HighscoreProfile> {
    var listInOrder = list
    listInOrder.sortByDescending { it.score }
    if(listInOrder.size > 4) {
        listInOrder = listInOrder.slice(0..4) as MutableList<HighscoreProfile>
    }
    return listInOrder
}

/**
 * Loads profiles from preferences.
 *
 * @param[key] key for loading
 * @param[callback] function to be called when data is done loading
 */
fun Activity.loadProfile(key: String, callback: (MutableList<PlayerProfile>) -> Unit) {
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE) ?: return
    val profile = pref.getString(key, "")

    try {
        var profileList = ObjectMapper().readValue(profile,
                object : TypeReference<MutableList<PlayerProfile>>() {})
        callback(profileList)
    } catch(e: Exception) {
        callback(mutableListOf())
    }
}

/**
 * Saves profiles to preferences.
 *
 * @param[key] key for saving
 * @param[profile] profile to be saved
 */
fun Activity.saveProfile(key: String, profile: PlayerProfile) {
    var list = mutableListOf<PlayerProfile>()
    this.loadProfile(key) {
        list = it
    }
    list.add(profile)
    var json: String = ObjectMapper().writeValueAsString(list)
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE) ?: return
    with (pref.edit()) {
        putString(key, json)
        apply()
    }
}

/**
 * Resets preferences from given key.
 *
 * @param[key] key to reset preferences
 */
fun Activity.resetPrefs(key: String) {
    val pref = this.getSharedPreferences(
            key, Context.MODE_PRIVATE)
    pref.edit().clear().commit()
}


