package com.im.kiai.ui.location

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.preference.PreferenceManager

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.im.kiai.R
import com.im.kiai.model.Kajian
import kotlinx.android.synthetic.main.activity_kajian_location.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class NearestLocationActivity : AppCompatActivity(), OnMapReadyCallback, NearestLocationView {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var mMap: GoogleMap
    var latitude: String? = null
    var longitude: String? = null
    var kajians: List<Kajian> = mutableListOf()
    private lateinit var presenter: NearestLocationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_kajian_location)


        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Lokasi Terdekat"


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        latitude = sharedPref.getString("LATITUDE", null)
        longitude = sharedPref.getString("LONGITUDE", null)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        presenter = NearestLocationPresenter(this, this)
        if(latitude==null) presenter.kajian()
        else presenter.kajianNearest(latitude!!, longitude!!)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener {
            card_info.visibility = View.GONE
        }

        mMap.setOnMarkerClickListener {
            onMarkerClick(it)
            return@setOnMarkerClickListener true
        }

        // Add a marker in Sydney and move the camera
//        val loc = LatLng(-7.462934148189172, 112.73736730255575)
//        mMap.addMarker(MarkerOptions().position(loc).title("Marker in Sydney"))
//        val builder = LatLngBounds.builder()
//        builder.include(loc)
//
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))

    }


    private fun onMarkerClick(it: Marker){
        var items = kajians.filter { data ->
            Log.d("Aaa", "cek ${data.judul} ,, ${it.title}")
            data.nama == it.title
        }
        if(items.isNotEmpty()) showCardInfo(items[0])
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showKajian(list: List<Kajian>) {
        kajians = list
        Log.d("NearestLocationActivity","cek ${list.size}")
        val builder = LatLngBounds.builder()
        if(list.isNotEmpty()) {
            builder.include(LatLng(list[0].latitude.toDouble(), list[0].longitude.toDouble()))
            showCardInfo(list[0])
            list.forEach {
                val loc = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
                mMap.addMarker(MarkerOptions().position(loc).title(it.nama))

            }
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
        }else{
            showMessage("Tidak ada kajian terdekat dengan lokasi anda")
            if(latitude!=null) {
                var loc = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
                builder.include(loc)
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100))
            }
        }

    }

    private fun showCardInfo(data: Kajian){
        Log.d("NearestLocationActivity","cek ${data}")
        card_info.visibility = View.VISIBLE
        tv_place_name.text = data.nama
        tv_presenter.text = data.asatidz
        tv_title.text = data.judul
        tv_time.text = data.waktu
        tv_date.text = getDateFormat(data.tanggal)

    }

    private fun getDateFormat(string: String?): String? {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputSDF = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault())
        var date: Date? = null
        date = try {
            //here you get Date object from string
            print("parse $string")
            inputSDF.parse(string)
        } catch (e: ParseException) {
            print("error ${e.message}")
            Log.d("MainActivity", "error ${e.message}")
            return string
        }
        //after changing date format again you can change to string with changed format
        return outputSDF.format(date)
    }
}