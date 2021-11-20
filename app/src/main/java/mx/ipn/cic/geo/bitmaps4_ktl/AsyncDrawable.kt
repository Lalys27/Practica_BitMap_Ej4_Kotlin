package mx.ipn.cic.geo.bitmaps4_ktl

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import java.lang.ref.WeakReference

// Solve problem using ListView and GridView
class AsyncDrawable(resources: Resources?, bitmap: Bitmap?, bitmapWorkerTask: BitmapWorkerTask?) :
  BitmapDrawable(resources, bitmap) {
  private val bitmapWorkerTaskReference: WeakReference<BitmapWorkerTask> = WeakReference(bitmapWorkerTask)

  val bitmapWorkerTask: BitmapWorkerTask?
    get() = bitmapWorkerTaskReference.get()
}