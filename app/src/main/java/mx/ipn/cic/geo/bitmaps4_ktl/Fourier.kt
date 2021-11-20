package mx.ipn.cic.geo.bitmaps4_ktl

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import org.jtransforms.fft.DoubleFFT_2D


import java.lang.ref.WeakReference

class Fourier(progressBar: ProgressBar, sourceImage: Bitmap) :     Algorithm(progressBar, sourceImage) {

    protected override fun doInBackground(vararg params: ImageView?): Bitmap? {
        this.imageViewReference = WeakReference(params[0])

        // Obtener los valores y aplicar la transformada FFT.
        val height: Int? = this.sourceImage?.height
        val width: Int? = this.sourceImage?.width
        var result: Int
        var pixelColor: Int
        // Para almacenar los valores de la imagen.
        val pixels = IntArray(width!! * height!!)
        // Array que contendrá el resultado de la transformación.
        val data = Array(height) {
            DoubleArray(width * 2)
        }

        // Generar la matriz para calcular la FFT.
        this.sourceImage?.getPixels(pixels, 0, width, 0, 0, width, height)
        // Hacer un barrido top-down en la imagen a procesar.
        for (y in 0 until height) {
            for (x in 0 until width) {
                // Obtener el color del pixel en la posición actual.
                pixelColor = pixels[y * width + x]
                data[y][x] = Color.green(pixelColor).toDouble()
            }
            // Mostrar en progreso del algoritmo.
            if (this.progressBarReference != null && this.progressBarReference?.get() != null) publishProgress()
        }
        val fourier = DoubleFFT_2D(height.toLong(), width.toLong())
        // Calcular la FFT.
        fourier.complexForward(data)
        // Calcular la FFT inversa.
        fourier.complexInverse(data, true)
        this.progressBarReference?.get()?.setProgress(0)

        // Hacer un barrido top-down en la imagen a procesar.
        for (y in 0 until height) {
            for (x in 0 until width) {
                // Obtener el color del pixel en la posición actual.
                result = data[y][x].toInt()
                this.resultImage?.setPixel(x, y, Color.rgb(result, result, result))
            }
            // Mostrar en progreso del algoritmo.
            if (this.progressBarReference != null && this.progressBarReference?.get() != null) publishProgress()
        }
        return this.resultImage
    }

    protected override fun onProgressUpdate(vararg params: Void?) {
        if (this.progressBarReference != null && this.progressBarReference?.get() != null)
            this.progressBarReference?.get()?.incrementProgressBy(1)
    }

    // Once complete, see if ImageView is still around and set bitmap.
    protected override fun onPostExecute(bitmap: Bitmap?) {
        Log.d("onPostExecute", "Algoritmo finalizó correctamente...")
        if (this.imageViewReference != null && bitmap != null) {
            val imageView: ImageView = imageViewReference?.get()!!
            if (imageView != null) {
                imageView.setImageBitmap(bitmap)
                imageView.postInvalidate()
            }
        }
    }

    init {
        // TODO Auto-generated constructor stub
        progressBar.max = sourceImage.height
    }
}