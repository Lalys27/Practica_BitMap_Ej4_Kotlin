package mx.ipn.cic.geo.bitmaps4_ktl

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import java.lang.ref.WeakReference
import kotlin.math.max
import kotlin.math.roundToInt

class BitmapWorkerTask(imageView: ImageView, context: Context, width: Int, height: Int) : AsyncTask<Int?, Void?, Bitmap>() {

    private var imageViewReference: WeakReference<ImageView?> = WeakReference(imageView)
    private var context: Context? = context
    private var width: Int = width
    private var height: Int = height
    private var data: Int = 0

    override fun doInBackground(vararg params: Int?): Bitmap? {
        for (p in params) {
            data = p!!
            break;
        }
        Log.d("doInBackground", "data: " + data)
        Log.d("doInBackground", "width: " + width + " height: " + height)
        val imageView = imageViewReference.get()
        return if (imageView != null) {
            decodeSampledBitmapFromResource(context!!.resources, data, width, height)
        } else null
    }


    // Once complete, see if ImageView is still around and set bitmap.
    override fun onPostExecute(bitmap: Bitmap) {
        Log.d("onPostExecute", "Se ejecuta onPostExecute")
        if (imageViewReference != null && bitmap != null) {
            Log.d("onPostExecute", "If IN")
            val imageView = imageViewReference.get()!!
            Log.d("onPostExecute", "imageView: " + imageView)
            Log.d("onPostExecute", "bitmap: " + bitmap)
            imageView.setImageBitmap(bitmap)
            imageView.invalidate()
        }
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    fun decodeSampledBitmapFromResource(res: Resources?, resId: Int, reqWidth: Int, reqHeight: Int,): Bitmap? {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

}