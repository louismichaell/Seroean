package com.seroean.apps.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.StrictMode
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.seroean.apps.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val SETTINGS_KEY = "settings"
const val ONBOARDING = "onboarding"
const val FINISHED = "finished"
const val DELAY_TIME: Long = 1000
const val countdownDuration: Long = 30000
const val slideInterval = 3000L

object VARIABEL {
    const val EMAILGOOGLE = "EXTRA_EMAIL"
    const val NAMEGOOGLE = "EXTRA_NAME"
    const val EMAIL = "EMAIL"
    const val EXTRA_WISATA_ID = "EXTRA_WISATA_ID"
    const val EXTRA_BUDAYA_ID = "EXTRA_BUDAYA_ID"
    const val EXTRA_SEJARAH_ID = "EXTRA_SEJARAH_ID"
    const val EXTRA_KULINER_ID = "EXTRA_KULINER_ID"
    const val EXTRA_PERTANYAAN_ID = "EXTRA_PERTANYAAN_ID"
    const val NAME = "NAME"
    const val LOCATION = "LOCATION"
    const val PASSWORD = "PASSWORD"
}

fun Activity.customToast(title: String, subtitle: String, style: MotionToastStyle) {
    MotionToast.createColorToast(
        this,
        title,
        subtitle,
        style,
        MotionToast.GRAVITY_TOP or Gravity.FILL_HORIZONTAL,
        MotionToast.LONG_DURATION,
        ResourcesCompat.getFont(this, R.font.helvetica)
    )
}

fun Fragment.customToast(title: String, subtitle: String, style: MotionToastStyle) {
    activity?.let { act ->
        MotionToast.createColorToast(
            act,
            title,
            subtitle,
            style,
            MotionToast.GRAVITY_TOP or android.view.Gravity.FILL_HORIZONTAL,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(act, R.font.helvetica)
        )
    }
}

fun lightStatusBar(window: Window, isLight: Boolean = true) {
    val wic = WindowInsetsControllerCompat(window, window.decorView)
    wic.isAppearanceLightStatusBars = isLight
}

fun ImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}

suspend fun downloadImageAndSave(context: Context, imageUrl: String, fileName: String): File? {
    return withContext(Dispatchers.IO) {
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        var file: File? = null

        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            inputStream = connection.inputStream


            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            file = File(storageDir, fileName)

            outputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var bytesRead: Int

            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
            outputStream?.close()
        }

        file
    }
}


fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}

fun convertDateTime(datetime: String?): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Jakarta")

        val date = inputFormat.parse(datetime.toString())
        outputFormat.format(date!!)
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }
}

fun convertBitmap(context: Context, urlString: String): Bitmap {
    return try {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        val url = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        BitmapFactory.decodeStream(input)
    } catch (e: IOException) {
        BitmapFactory.decodeResource(context.resources, R.drawable.ic_logoapp)
    }
}

fun getTimeAgo(time: Long): String {
    val currentTime = System.currentTimeMillis()
    val diff = currentTime - time

    val minute = 60 * 1000
    val hour = 60 * minute
    val day = 24 * hour

    return when {
        diff < minute -> "just now"
        diff < 2 * minute -> "a minute ago"
        diff < 50 * minute -> "${diff / minute} minutes ago"
        diff < 90 * minute -> "an hour ago"
        diff < 24 * hour -> "${diff / hour} hours ago"
        diff < 48 * hour -> "yesterday"
        else -> "${diff / day} days ago"
    }
}