package com.im.kiai.ui.kajian

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.im.kiai.R
import com.im.kiai.model.Kajian
import com.im.kiai.network.ApiService
import kotlinx.android.synthetic.main.activity_detail_kajian.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class DetailKajianActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_kajian)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        val data = intent.getParcelableExtra<Kajian>("data")
        toolbar.title = "Info Kajian"
        if(data!=null) setData(data)
        val previewRequest =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
//                    if(it.data?.getBooleanExtra("refresh", false) == true && role == "ADMIN") presenter.getAllReport()
//                    else if(it.data?.getBooleanExtra("refresh", false) == true) presenter.getReport(rt.toInt(), rw.toInt())
                }
            }

        btn_direction.setOnClickListener {
            val strUri =
                "http://maps.google.com/maps?q=loc:" + data?.latitude.toString() + "," + data?.longitude.toString() + " (" + data?.nama.toString() + ")"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))
            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )
            startActivity(intent)
        }
    }

    fun setData(item: Kajian){
        tv_title.text = item.judul
        tv_presenter.text = item.asatidz
        tv_place.text = "${item.nama} "
        tv_location.text = "${item.alamat}"

        var dateFormatted = getDateFormat(item.tanggal)
        tv_date.text = "$dateFormatted ${item.waktu}"


        var img = "${ApiService.URL}storage/${item.poster}"
        Glide.with(this)
            .load(img)
            .into( iv_poster)
        if((item.streaming ?: "").isNotEmpty()) {
            btn_stream.visibility = View.VISIBLE
            btn_stream.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(item.streaming)
                intent.setPackage("com.google.android.youtube")
                startActivity(intent)
            }
        }
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


    public override fun onStart() {
        super.onStart()

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