package fi.tuni.dontstutter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * @author Juliana Pääkkönen
 * @version 2021.0520
 * @since 1.4.31
 */

/**
 * Creates custom adapter for Highscore's ListView
 *
 * @property[context] context
 * @property[list] highscore list
 */
class HighscoreAdapter(var context: Context, var list: ArrayList<HighscoreProfile>): BaseAdapter() {

    /**
     * Gets list size.
     *
     * @return list size
     */
    override fun getCount(): Int {
        return list.size
    }

    /**
     * Gets item from list.
     *
     * @return one item from list
     */
    override fun getItem(index: Int): Any {
        return list[index]
    }

    /**
     * Gets row id (index).
     *
     * @return row id
     */
    override fun getItemId(index: Int): Long {
       return index.toLong()
    }

    /**
     * Creates a new view for ListView.
     *
     * @param[index] index
     * @param[convertView] old view
     * @param[viewGroup] parent
     * @return created View
     */
    override fun getView(index: Int, convertView: View?, viewGroup: ViewGroup?): View {
        var view = convertView
        lateinit var bitmap: Bitmap

        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup,
                    false)
        }

        val highscore = this.getItem(index) as HighscoreProfile
        val img = view!!.findViewById(R.id.listpic) as ImageView
        val info = view.findViewById(R.id.listitem) as TextView
        info.text = highscore.toString()

        if(highscore.imgPath == R.drawable.defaultprofpic.toString()) {
            bitmap = BitmapFactory.decodeResource(context.resources, highscore.imgPath.toInt())
            bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, false)
        } else {
            setPic(highscore.imgPath) {
                bitmap = it
            }
        }
        img.setImageBitmap(bitmap)
        return view
    }
}
