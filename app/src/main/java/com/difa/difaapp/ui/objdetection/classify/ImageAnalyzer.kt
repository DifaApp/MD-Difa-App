package com.difa.difaapp.ui.objdetection.classify

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.difa.difaapp.data.local.entity.Recognition
import com.difa.difaapp.ml.DifaModel
import com.difa.difaapp.utils.RgbConverter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.model.Model
import kotlin.random.Random

typealias RecognitionListener = (recognition: List<Recognition>) -> Unit
class ImageAnalyzer(context: Context, private val listener: RecognitionListener) : ImageAnalysis.Analyzer{

    private val difaModel: DifaModel by lazy {
        // TODO 6. Optional GPU acceleration
        val compatList = CompatibilityList()

        val options = if (compatList.isDelegateSupportedOnThisDevice) {
            Log.d(TAG, "This device is GPU Compatible ")
            Model.Options.Builder().setDevice(Model.Device.GPU).build()
        } else {
            Log.d(TAG, "This device is GPU Incompatible ")
            Model.Options.Builder().setNumThreads(4).build()
        }

        // Initialize the Flower Model
        DifaModel.newInstance(context, options)
    }

    private val rgbConverter = RgbConverter(context)
    private lateinit var bitmapBuffer: Bitmap
    private lateinit var rotationMatrix: Matrix

    override fun analyze(image: ImageProxy) {
        val items = mutableListOf<Recognition>()

        val tfImage = TensorImage.fromBitmap(toBitmap(image))

        // TODO 3: Process the image using the trained model, sort and pick out the top results
        val outputs = difaModel.process(tfImage)
            .probabilityAsCategoryList.apply {
                sortByDescending { it.score } // Sort with highest confidence first
            }.take(MAX_RESULT_DISPLAY) // take the top results

        // TODO 4: Converting the top probability items into a list of recognitions
        for (output in outputs) {
            items.add(Recognition(output.label, output.score))
        }

        // Return the result
        listener(items.toList())

        // Close the image,this tells CameraX to feed the next image to the analyzer
        image.close()
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun toBitmap(imageProxy: ImageProxy): Bitmap? {
        val image = imageProxy.image ?: return null

        // Initialise Buffer
        if (!::bitmapBuffer.isInitialized) {
            // The image rotation and RGB image buffer are initialized only once
            Log.d(TAG, "Initalise toBitmap()")
            rotationMatrix = Matrix()
            rotationMatrix.postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
            bitmapBuffer = Bitmap.createBitmap(
                image.width, image.height, Bitmap.Config.ARGB_8888
            )
        }

        // Pass image to an image analyser
        rgbConverter.yuvToRgb(image, bitmapBuffer)

        // Create the Bitmap in the correct orientation
        return Bitmap.createBitmap(
            bitmapBuffer,
            0,
            0,
            bitmapBuffer.width,
            bitmapBuffer.height,
            rotationMatrix,
            false
        )
    }


    companion object{
        private const val TAG = "ImageAnalyzer"
        private const val MAX_RESULT_DISPLAY = 3
    }

}