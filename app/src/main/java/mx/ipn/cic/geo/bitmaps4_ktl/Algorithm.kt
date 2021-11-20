package mx.ipn.cic.geo.bitmaps4_ktl

import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import java.lang.Exception
import java.lang.ref.WeakReference

open class Algorithm(progressBar: ProgressBar?, sourceImage: Bitmap?) :
    AsyncTask<ImageView?, Void?, Bitmap?>() {

    protected var imageViewReference: WeakReference<ImageView>? = null
    protected var progressBarReference: WeakReference<ProgressBar>? = null
    protected var sourceImage: Bitmap? = null
    protected var resultImage: Bitmap? = null

    init {
        try {
            // Asignar la referencia a la imagen original a manipular.
            this.sourceImage = sourceImage
            // Crear una imagen donde se guardarán los resultados del algoritmo.
            resultImage = Bitmap.createBitmap(this.sourceImage!!.width,
                this.sourceImage!!.height,
                this.sourceImage!!.config)
            // Asignar la referencia al control que mostrará el progreso del algoritmo.
            if (progressBar != null) progressBarReference = WeakReference(progressBar)
        } catch (e: Exception) {
            // Escribir el mensaje de error en el log.
            Log.e("Algorithm", "Ocurrió un error en la creación del mapa de bits$e")
            this.resultImage = null
            this.sourceImage = null
        }
    }

    override fun doInBackground(vararg p0: ImageView?): Bitmap? {
        TODO("Not yet implemented")
    }
}