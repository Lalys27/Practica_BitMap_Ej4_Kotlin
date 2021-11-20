package mx.ipn.cic.geo.bitmaps4_ktl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.view.Menu
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var buttonDrawBitmap: Button
    private lateinit var buttonConvertBitmap: Button
    private lateinit var buttonFourier: Button
    private lateinit var imageViewSourceBitmap: ImageView
    private lateinit var imageViewResultBitmap: ImageView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonDrawBitmap = findViewById(R.id.buttonDrawBitmap)
        buttonConvertBitmap = findViewById(R.id.buttonConvertBitmap)
        buttonFourier = findViewById(R.id.buttonFourier)
        imageViewSourceBitmap = findViewById(R.id.imageViewSourceBitmap)
        imageViewResultBitmap = findViewById(R.id.imageViewResultBitmap)
        progressBar = findViewById(R.id.progressBar)


        buttonDrawBitmap.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(view: View?) {
//                    imageViewSourceBitmap.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.imagen0))
                    LoadImagesIntoImageView()
                }
            }
        )

        buttonConvertBitmap.setOnClickListener {
            progressBar.setProgress(0)
            val sourceBitmap = (imageViewSourceBitmap.drawable as BitmapDrawable).bitmap
            progressBar.setMax(imageViewSourceBitmap.height + 1)
            val algorithm = GrayScale(progressBar, sourceBitmap)
            algorithm.execute(imageViewResultBitmap)
        }

        buttonFourier.setOnClickListener {
            progressBar.setProgress(0)
            val sourceBitmap = (imageViewSourceBitmap.drawable as BitmapDrawable).bitmap
            progressBar.setMax(imageViewSourceBitmap.height + 1)
            val algorithm = Fourier(progressBar, sourceBitmap)
            algorithm.execute(imageViewResultBitmap)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemMenuLoadBitmap -> {
                LoadImagesIntoImageView()
                return true
            }
            R.id.itemMenuExitApp -> {
                finish()
                System.exit(RESULT_OK)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    fun LoadImagesIntoImageView() {
        Log.d("LoadImagesIntoImageView", "width: " + imageViewSourceBitmap.width + " height: " + imageViewSourceBitmap.height)
        // Compute a random number to determine the image.
        val randomNumber = Math.ceil(Math.random() * 9.0).toInt()
        val taskSource = BitmapWorkerTask(
            imageViewSourceBitmap,
            applicationContext,
            imageViewSourceBitmap.width,
            imageViewResultBitmap.height
        )
        val taskResult = BitmapWorkerTask(
            imageViewResultBitmap,
            applicationContext,
            imageViewResultBitmap.width,
            imageViewResultBitmap.height
        )
        when (randomNumber) {
            0 -> {
                taskSource.execute(R.drawable.imagen0)
                taskResult.execute(R.drawable.imagen0)
            }
            1 -> {
                taskSource.execute(R.drawable.imagen1)
                taskResult.execute(R.drawable.imagen1)
            }
            2 -> {
                taskSource.execute(R.drawable.imagen2)
                taskResult.execute(R.drawable.imagen2)
            }
            3 -> {
                taskSource.execute(R.drawable.imagen3)
                taskResult.execute(R.drawable.imagen3)
            }
            4 -> {
                taskSource.execute(R.drawable.imagen4)
                taskResult.execute(R.drawable.imagen4)
            }
            5 -> {
                taskSource.execute(R.drawable.imagen5)
                taskResult.execute(R.drawable.imagen5)
            }
            6 -> {
                taskSource.execute(R.drawable.imagen6)
                taskResult.execute(R.drawable.imagen6)
            }
            7 -> {
                taskSource.execute(R.drawable.imagen7)
                taskResult.execute(R.drawable.imagen7)
            }
            8 -> {
                taskSource.execute(R.drawable.imagen8)
                taskResult.execute(R.drawable.imagen8)
            }
            9 -> {
                taskSource.execute(R.drawable.imagen9)
                taskResult.execute(R.drawable.imagen9)
            }
        }
    }
}