package com.seroean.apps.ui.notifikasi

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.seroean.apps.R
import com.seroean.apps.data.response.NotifikasiResponse
import com.seroean.apps.data.retrofit.ApiConfig
import com.seroean.apps.ui.navigation.FragmentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class NotifikasiWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        checkForNewNotifications()
        return Result.success()
    }

    private fun checkForNewNotifications() {
        val token = inputData.getString("TOKEN") ?: return

        ApiConfig.getApiService().getNotifikasi("Bearer $token").enqueue(object :
            Callback<NotifikasiResponse> {
            override fun onResponse(call: Call<NotifikasiResponse>, response: Response<NotifikasiResponse>) {
                if (response.isSuccessful) {
                    val notifications = response.body()?.data
                    if (!notifications.isNullOrEmpty()) {
                        val latestNotification = notifications.first()
                        val sharedPref = applicationContext.getSharedPreferences("NOTIF_PREF", Context.MODE_PRIVATE)

                        val lastNotifiedId = sharedPref.getInt("LAST_NOTIF_ID", -1)

                        if (System.currentTimeMillis().toInt() != lastNotifiedId) {
                            sendNotification(latestNotification.title, System.currentTimeMillis().toInt()) // Gunakan ID unik


                            // Simpan ID notifikasi terbaru agar tidak mengirim berulang
                            sharedPref.edit().putInt("LAST_NOTIF_ID", System.currentTimeMillis().toInt()).apply()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<NotifikasiResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun sendNotification(title: String, notificationId: Int) {
        val context = applicationContext
        val channelId = "NOTIFICATION_CHANNEL"

        val intent = Intent(context, FragmentActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                context.getString(R.string.NotifikasiTitle),
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                return
            }
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.NotifikasiContent))
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        NotificationManagerCompat.from(context).notify(notificationId, notification) // Gunakan ID unik
    }

    companion object {
        fun scheduleWorker(context: Context, token: String) {
            val workManager = WorkManager.getInstance(context)

            val inputData = workDataOf("TOKEN" to token)

            val request = PeriodicWorkRequestBuilder<NotifikasiWorker>(15, TimeUnit.MINUTES)
                .setInputData(inputData)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED) // Hanya berjalan saat ada internet
                        .build()
                )
                .build()

            workManager.enqueueUniquePeriodicWork(
                "NotifikasiWorker",
                ExistingPeriodicWorkPolicy.KEEP, // Pastikan tidak dijadwalkan ulang jika sudah ada
                request
            )
        }
    }
}
