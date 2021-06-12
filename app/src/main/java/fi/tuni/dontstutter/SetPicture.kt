package fi.tuni.dontstutter

import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Scales picture for imageview and makes it bitmap.
 *
 * @property[picPath] picture to be altered
 * @property[callback] function to be called
 */
fun setPic(picPath: String?, callback: (Bitmap) -> Unit) {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = false
        BitmapFactory.decodeFile(picPath, this)
        val scaleFactor: Int = Math.max(1, Math.min(outWidth / 200, outHeight / 200))
        inJustDecodeBounds = false
        inSampleSize = scaleFactor
        inPurgeable = true
    }

    BitmapFactory.decodeFile(picPath, options)?.also { bitmap ->
        callback(Bitmap.createBitmap(bitmap, 0, 0, 200, 200))
    }
}
