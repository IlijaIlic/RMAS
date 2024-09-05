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
import androidx.lifecycle.ViewModel
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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NearbyService() : Service() {

    //CHANNEL ID za NOTIFCATION CHANNEL
    private val CHANNEL_ID = "ProximityServiceChannel"

    //ZA PRACENJE LOKACIJE
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback


    //Foreground Servis pa ne treba onBind
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
        Toast.makeText(this, "Servis pokrenut", Toast.LENGTH_SHORT).show()
        pratiLokaciju()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        stopSelf()

        if (::fusedLocationClient.isInitialized && ::locationCallback.isInitialized) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
        Toast.makeText(this, "Servis iskljucen", Toast.LENGTH_SHORT).show()
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
            .setOngoing(true)
            .setContentIntent(pendingIntent)
            .build()
    }


    @SuppressLint("MissingPermission")
    private fun pratiLokaciju() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //prati lokaciju svaki minut
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            20000
        ).build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location: Location = locationResult.lastLocation!!
                proveriBlizinu(location)
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun proveriBlizinu(userLocation: Location) {
        val oglasRepository = OglasRepository(oglasDS = OglasDS())
        CoroutineScope(Dispatchers.IO).launch {
            val sales = oglasRepository.getAllSales() // Get sales from repository

            val todaysDateUNF = System.currentTimeMillis()
            val todaysDate = Date(todaysDateUNF)
            val dateFormat = SimpleDateFormat("dd. MMMM yyyy.", Locale("bs", "BS", "LAT"))

            val filteredOglasi = sales?.documents?.filter { sale ->
                val saleDate = dateFormat.parse(sale.getString("datumIsteka")!!)
                saleDate?.after(todaysDate) ?: false
            }


            filteredOglasi?.forEach { sale ->

                val lat = sale.getField<Double>("lat") ?: 0.0
                val lng = sale.getField<Double>("lng") ?: 0.0
                val distance = izracunajUdaljenost(
                    userLocation.latitude, userLocation.longitude,
                    lat, lng
                )

                if (distance <= 0.5) {
                    pushNotification(sale)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun pushNotification(sale: DocumentSnapshot) {

        val id = sale.id.hashCode()
        val prod = sale.getString("prodavnica")

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("sale_id", sale.id)
        }

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Akcija je blizu!")
            .setContentText("Nalazite se blizu akcije prodavnice ${prod}!")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(this).notify(id, notification)
    }

    //Funkcija sa interneta !!
    //Radi valjda !
    private fun izracunajUdaljenost(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
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