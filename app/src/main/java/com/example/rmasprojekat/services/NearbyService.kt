package com.example.rmasprojekat.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlin.math.*
import android.R
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.rmasprojekat.MainActivity
import com.example.rmasprojekat.classes.Sale
import com.example.rmasprojekat.ds.OglasDS
import com.example.rmasprojekat.repositories.OglasRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.getField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NearbyService() : Service() {

    //CHANNEL ID za NOTIFCATION CHANNEL
    private val CHANNEL_ID = "ProximityServiceChannel"

    //Foreground Service
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        createNotifChannel()

        val notif = createNotif()
        startForeground(1, notif)
    }

    //Poziva se iz profile kad je aktivno
    //Promeniti i u FIRESTORE-U service na TRUE
    //PRI POKRETANJU APLIKACIJE PROVERITI DA LI JE U FIRESTORE-U TRUE AKOE JESTE UKLJUCITI SERVICE
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.w("SERVICE", "SERVICE POKRENUT")
        monitorLocation()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Servis iskljucen", Toast.LENGTH_SHORT).show()
        //fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    //Kreiranje kanala za notifikacije
    private fun createNotifChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Kanal NearbyService-a"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = "Kanal za servis NearbyService"
                }
            val notifManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notifManager.createNotificationChannel(channel)
        }
    }

    private fun createNotif(): Notification {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("NearbyService ukljucen")
            .setContentText("Pracenje lokacije")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .build()
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    private fun monitorLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            60000
        ).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location: Location = locationResult.lastLocation!!
                checkProximityToSales(location)
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun checkProximityToSales(userLocation: Location) {
        val oglasRepository = OglasRepository(oglasDS = OglasDS())
        CoroutineScope(Dispatchers.IO).launch {
            val sales = oglasRepository.getAllSales() // Get sales from repository
            if (sales != null) {
                for (sale in sales.documents) {
                    val lat = sale.getField<Double>("lat") ?: 0.0
                    val lng = sale.getField<Double>("lng") ?: 0.0
                    val distance = calculateDistance(
                        userLocation.latitude, userLocation.longitude,
                        lat, lng
                    )

                    if (distance <= 1.0) { // 1 km radius
                        pushNotification(sale)
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun pushNotification(sale: DocumentSnapshot) {

        val id = (sale.getDouble("lat")!! + sale.getDouble("lng")!!).toInt()

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("sale_id", sale.id)
        }

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Akcija je blizu!")
            .setContentText("Nalazite se blizu akcije!")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(id, notification)
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a =
            sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(
                2
            )
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return earthRadius * c
    }

}