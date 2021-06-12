package fi.tuni.dontstutter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Activity to display current profiles.
 *
 * @property[profileView] ListView to display profiles
 * @property[adapter] custom adapter for ListView
 * @property[activeprof] displays active profile
 * @property[currentProf] index of active profile
 * @property[arraylist] list of profiles
 */
class Profile : AppCompatActivity() {
    lateinit var profileView: ListView
    lateinit var adapter: ProfileAdapter
    lateinit var activeprof: TextView
    var currentProf = 0
    lateinit var arraylist: ArrayList<PlayerProfile>

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)
        this.profileView = findViewById(R.id.listView)
        this.activeprof = findViewById(R.id.activeprof)

        this.loadProfile("profile") {
            arraylist = ArrayList(it)
        }
        this.loadCurrentProf("currprof") {
            currentProf = it
            this.loadProfile("profile") {
                if(it.isNotEmpty()) {
                    activeprof.text = "Now active profile: " + it[currentProf].name
                }
            }
        }

        adapter = ProfileAdapter(this, arraylist)
        profileView.adapter = adapter
        profileView.setOnItemClickListener { _, _, index, _ ->
            currentProf = index
            this.saveCurrentProf("currprof", currentProf)
            this.loadProfile("profile") {
                if(it.isNotEmpty()) {
                    activeprof.text = "Now active profile: " + it[currentProf].name
                }
            }
        }
    }

    /**
     * Opens NewProfile where user can add a new profile.
     *
     * Called when "new" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun addClicked(button: View) {
        startActivity(Intent(this, NewProfile::class.java))
    }

    /**
     * Goes back to main menu.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
