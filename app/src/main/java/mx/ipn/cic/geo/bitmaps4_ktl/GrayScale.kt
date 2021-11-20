package mx.ipn.cic.geo.bitmaps4_ktl

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import java.lang.ref.WeakReference

class GrayScale(progressBar: ProgressBar, sourceImage: Bitmap) : Algorithm(progressBar, sourceImage) {

    private enum class OptionMethod {
        GREEN_VALUE, YIQ_EQUATION, READ_LINE, READ_IMAGE
    }

    protected override fun doInBackground(vararg params: ImageView?): Bitmap? {
        this.imageViewReference = WeakReference(params[0])
        val option = OptionMethod.READ_IMAGE
        if (option == OptionMethod.GREEN_VALUE) return convertUsingGreen()
        if (option == OptionMethod.YIQ_EQUATION) return convertUsingYIQ()
        if (option == OptionMethod.READ_LINE) return convertReadLine()
        return if (option == OptionMethod.READ_IMAGE) convertReadImage() else null
    }

    private fun convertUsingGreen(): Bitmap? {
        val width: Int? = this.sourceImage?.width
        val height: Int? = this.sourceImage?.height
        var green: Int
        var pixelColor: Int?

        // Hacer un barrido top-down en la imagen a procesar.
        for (y in 0 until height!!) {
            for (x in 0 until width!!) {
                // Obtener el color del pixel en la posición actual.
                pixelColor = this.sourceImage?.getPixel(x, y)
                green = Color.green(pixelColor!!)
                this.resultImage?.setPixel(x, y, Color.rgb(green, green, green))
            }
            // Mostrar en progreso del algoritmo.
            if (this.progressBarReference != null && this.progressBarReference?.get() != null) publishProgress()
        }
        return this.resultImage
    }

    private fun convertUsingYIQ(): Bitmap? {
        val width: Int? = this.sourceImage?.width
        val height: Int? = this.sourceImage?.height
        var result: Int
        var pixelColor: Int

        // Hacer un barrido top-down en la imagen a procesar.
        for (y in 0 until height!!) {
            for (x in 0 until width!!) {
                // Obtener el color del pixel en la posición actual.
                pixelColor = this.sourceImage?.getPixel(x, y)!!
                // Obtener el nivel de gris usando ecuación modelo YIQ.
                result = Math.round(0.30 * Color.red(pixelColor).toFloat() + 0.59 * Color.green(
                    pixelColor).toFloat() + 0.11 * Color.blue(pixelColor).toFloat())
                    .toInt()
                this.resultImage?.setPixel(x, y, Color.rgb(result, result, result))
            }
            // Mostrar en progreso del algoritmo.
            if (this.progressBarReference != null && this.progressBarReference!!.get() != null) publishProgress()
        }
        return this.resultImage
    }

    private fun convertReadLine(): Bitmap? {
        val width: Int? = this.sourceImage?.width
        val height: Int? = this.sourceImage?.height
        val pixels = IntArray(this.sourceImage?.width!!)
        var result: Int
        var pixelColor: Int

        // Hacer un barrido top-down en la imagen a procesar.
        for (y in 0 until height!!) {
            // Read a complete line from source bitmap.
            this.sourceImage?.getPixels(pixels, 0, width!!, 0, y, width, 1)
            for (x in 0 until width!!) {
                // Obtener el color del pixel en la posición actual.
                pixelColor = pixels[x]
                result = Math.round(0.30 * Color.red(pixelColor).toFloat() + 0.59 * Color.green(
                    pixelColor).toFloat() + 0.11 * Color.blue(pixelColor).toFloat())
                    .toInt()
                pixels[x] = Color.rgb(result, result, result)
            }
            this.resultImage?.setPixels(pixels, 0, width, 0, y, width, 1)
            // Mostrar en progreso del algoritmo.
            if (this.progressBarReference != null && this.progressBarReference!!.get() != null) publishProgress()
        }
        return this?.resultImage
    }
    fun multiply(x: Int, y: Int) = x * y

    private fun convertReadImage(): Bitmap? {
        val width: Int? = this.sourceImage?.width
        val height: Int? = this.sourceImage?.height
        val pixels = IntArray(multiply(width!!, height!!))
        var result: Int
        var pixelColor: Int

        // Read all bitmap information.
        this.sourceImage?.getPixels(pixels, 0, width!!, 0, 0, width, height!!)
        // Hacer un barrido top-down en la imagen a procesar.
        for (y in 0 until height!!) {
            for (x in 0 until width!!) {
                // Obtener el color del pixel en la posición actual.
                pixelColor = pixels[y * width + x]
                result = Math.round(0.30 * Color.red(pixelColor).toFloat() + 0.59 * Color.green(
                    pixelColor).toFloat() + 0.11 * Color.blue(pixelColor).toFloat())
                    .toInt()
                pixels[y * width + x] = Color.rgb(result, result, result)
            }
            // Mostrar en progreso del algoritmo.
            if (this.progressBarReference != null && this.progressBarReference!!.get() != null) publishProgress()
        }
        this.resultImage?.setPixels(pixels, 0, width!!, 0, 0, width!!, height!!)
        return this.resultImage
    }

    protected override fun onProgressUpdate(vararg params: Void?) {
        if (this.progressBarReference != null && this.progressBarReference!!.get() != null) this.progressBarReference!!.get()
            ?.incrementProgressBy(1)
    }

    // Once complete, see if ImageView is still around and set bitmap.
    protected override fun onPostExecute(bitmap: Bitmap?) {
        Log.d("onPostExecute", "Algoritmo finalizó correctamente...")
        if (this.imageViewReference != null && bitmap != null) {
            val imageView: ImageView? = imageViewReference!!.get()
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

