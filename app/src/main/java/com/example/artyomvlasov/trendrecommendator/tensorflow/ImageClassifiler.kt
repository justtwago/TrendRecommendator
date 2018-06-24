package com.example.artyomvlasov.trendrecommendator.tensorflow

import android.app.Activity
import android.graphics.Bitmap
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.AbstractMap
import java.util.ArrayList
import java.util.Comparator
import java.util.PriorityQueue
import org.tensorflow.lite.Interpreter
import java.io.*


private const val MODEL_PATH = "graph.lite"
private const val LABEL_PATH = "labels.txt"
private const val RESULTS_TO_SHOW = 1
private const val DIM_BATCH_SIZE = 1
private const val DIM_PIXEL_SIZE = 3
private const val DIM_IMG_SIZE_X = 224
private const val DIM_IMG_SIZE_Y = 224
private const val IMAGE_MEAN = 128
private const val IMAGE_STD = 128.0f
private const val LABEL_SIZE = 19
private const val FILTER_STAGES = 3
private const val FILTER_FACTOR = 0.4f

class ImageClassifier(activity: Activity) {

    private val intValues = IntArray(DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y)
    private val tflite: Interpreter

    private val labelList: List<String>

    private var imgData: ByteBuffer? = null

    private var labelProbArray: Array<FloatArray> = emptyArray()
    private var filterLabelProbArray: Array<FloatArray> = emptyArray()

    private val sortedLabels = PriorityQueue(
            RESULTS_TO_SHOW,
            Comparator<MutableMap.MutableEntry<String, Float>> { o1, o2 -> o1.value.compareTo(o2.value) })

    init {
        tflite = Interpreter(loadModelFile(activity))
        labelList = loadLabelList(activity)
        imgData = ByteBuffer.allocateDirect(
                4 * DIM_BATCH_SIZE * DIM_IMG_SIZE_X * DIM_IMG_SIZE_Y * DIM_PIXEL_SIZE)
        imgData!!.order(ByteOrder.nativeOrder())
        labelProbArray = Array(1) { FloatArray(labelList.size) }
        filterLabelProbArray = Array(FILTER_STAGES) { FloatArray(labelList.size) }
    }

    internal fun classifyFrame(bitmap: Bitmap): String {
        convertBitmapToByteBuffer(bitmap)
        tflite.run(imgData!!, labelProbArray)
        applyFilter()

        return printTopKLabels()
    }

    private fun applyFilter() {
        val num_labels = labelList.size

        for (j in 0 until num_labels) {
            filterLabelProbArray[0][j] += FILTER_FACTOR * (labelProbArray[0][j] - filterLabelProbArray[0][j])
        }
        for (i in 1 until FILTER_STAGES) {
            for (j in 0 until num_labels) {
                filterLabelProbArray[i][j] += FILTER_FACTOR * (filterLabelProbArray[i - 1][j] - filterLabelProbArray[i][j])

            }
        }

        for (j in 0 until num_labels) {
            labelProbArray[0][j] = filterLabelProbArray[FILTER_STAGES - 1][j]
        }
    }

    fun close() {
        tflite.close()
    }

    private fun loadLabelList(activity: Activity): List<String> {
        val file: InputStream = activity.assets.open(LABEL_PATH)
        val labelList = ArrayList<String>()
        file.bufferedReader().use { text ->
            for (i in 0 until LABEL_SIZE) {
                labelList.add(text.readLine())
            }
        }
        return labelList
    }

    private fun loadModelFile(activity: Activity): MappedByteBuffer {
        val fileDescriptor = activity.assets.openFd(MODEL_PATH)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
        if (imgData == null) {
            return
        }
        imgData!!.rewind()
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until DIM_IMG_SIZE_X) {
            for (j in 0 until DIM_IMG_SIZE_Y) {
                val `val` = intValues[pixel++]
                imgData!!.putFloat(((`val` shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                imgData!!.putFloat(((`val` shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                imgData!!.putFloat(((`val` and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
            }
        }
    }

    private fun printTopKLabels(): String {
        for (i in labelList.indices) {
            sortedLabels.add(
                    AbstractMap.SimpleEntry(labelList[i], labelProbArray[0][i]))
            if (sortedLabels.size > RESULTS_TO_SHOW) {
                sortedLabels.poll()
            }
        }
        return sortedLabels.poll().key
    }
}