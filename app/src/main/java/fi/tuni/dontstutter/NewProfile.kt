package fi.tuni.dontstutter

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Activity to create a new profile.
 *
 * @property[inputName] EditText that receives name for profile
 * @property[imageView] ImageView that displays profile picture
 * @property[warning] warning message to be displayed if user doesn't input name
 * @property[name] name for profile
 * @property[defaultImg] default profile picture (path)
 * @property[imgPath] profile picture (path)
 * @property[newProfile] new profile to be saved
 * @property[currImgPath] path where photo (from camera) is saved
 * @property[REQUEST_IMAGE_CAPTURE] request code
 */
class NewProfile : AppCompatActivity() {
    lateinit var inputName: EditText
    lateinit var imageView: ImageView
    lateinit var warning: TextView
    var name = "anonymous"
    val defaultImg = R.drawable.defaultprofpic.toString()
    var imgPath: String? = R.drawable.defaultprofpic.toString()
    lateinit var newProfile: PlayerProfile
    var currImgPath: String? = ""
    val REQUEST_IMAGE_CAPTURE = 1

    /**
     * Calls the super class and sets user interface layout.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_profile)
        this.inputName = findViewById(R.id.inputName)
        this.imageView = findViewById(R.id.profilePic)
        this.warning = findViewById(R.id.namewarning)
        setPic(imgPath) {
            this.imageView.setImageBitmap(it)
        }
    }

    /**
     * Saves current state.
     *
     * @param[outState] for saving state
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentPhotoPath", currImgPath)
    }

    /**
     * Restores data from saved state.
     *
     * @param[savedInstanceState] previously saved state
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currImgPath = savedInstanceState.getString("currentPhotoPath")
    }

    /**
     * Creates profile and saves it to preferences.
     *
     * Called when "save" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun submitClicked(button: View) {
        name = inputName.text.toString()
        if (name.isNotEmpty()) {
            if(name.length <= 10) {
                this.loadProfile("profile") {
                    val size = it.size
                    if (size < 5) {
                        newProfile = PlayerProfile(name, imgPath)
                        this.saveProfile("profile", newProfile)
                        this.saveCurrentProf("currprof", size)
                        Toast.makeText(this, "Saved!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, Profile::class.java))
                    } else {
                        Toast.makeText(this, "Maximum number of profiles",
                                Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                warning.text = "Max. 10 letters!"
            }
        } else {
            warning.text = "Name is required!"
        }
    }

    /**
     * Goes back to profiles.
     *
     * Called when "back" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun backClicked(button: View) {
        startActivity(Intent(this, Profile::class.java))
    }

    /**
     * Calls resetPic to use default picture instead of photo taken with camera.
     *
     * Called when "use default" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun useDefaultPressed(button: View) {
        resetPic()
    }

    /**
     * Resets back to default picture.
     */
    fun resetPic() {
        imgPath = defaultImg
        var bm = BitmapFactory.decodeResource(this.resources, defaultImg.toInt())
        this.imageView.setImageBitmap(bm)
    }

    /**
     * Calls takePhoto to open camera.
     *
     * Called when "take photo" is pressed.
     *
     * @param[button] button that calls the function
     */
    fun takePhotoClicked(button: View) {
        takePhoto()
    }

    /**
     * Creates path for saving photo.
     *
     * @return path where photo should be saved
     */
    fun createFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val file: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", file).apply {
            currImgPath = path
        }
    }

    /**
     * Calls createFile to get file for saving photo and opens camera.
     */
    fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                val imgFile: File? = try {
                    createFile()
                } catch (e: IOException) {
                    Toast.makeText(this, "Couldn't find destination file",
                                    Toast.LENGTH_LONG).show()
                    null
                }
                try {
                    imgFile?.also {
                        val uri: Uri = FileProvider.getUriForFile(this,
                                                        "fi.tuni.dontstutter", it)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Unable to use camera",
                                    Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * Photo path is set as imgPath (to be saved as profile pic) and photo is shown in imageview.
     *
     * Called when photo is taken.
     *
     * @param[requestCode] request code
     * @param[resultCode] result code
     * @param[data] intent from camera
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imgPath = currImgPath
            setPic(imgPath) {
                imageView.setImageBitmap(it)
            }

        }
    }
}

/**
 * Class for creating a new profile.
 *
 * @property[name] profile name
 * @property[img] profile picture
 */
data class PlayerProfile(val name: String = "anonymous", val img: String? =
                        R.drawable.defaultprofpic.toString()) {

    /**
     * Overrides toString.
     *
     * @return player's name
     */
    override fun toString(): String {
        return name
    }
}
