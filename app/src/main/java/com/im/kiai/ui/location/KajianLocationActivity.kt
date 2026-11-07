package com.im.kiai.ui.location

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.im.kiai.R
import com.im.kiai.network.message.response.Asatidz
import com.im.kiai.network.message.response.Locations
import com.im.kiai.ui.kajian.KajianActivity
import com.im.kiai.utils.enum.KajianType
import kotlinx.android.synthetic.main.activity_astidz.*


class KajianLocationActivity : AppCompatActivity(), KajianLocationView{
    private lateinit var sharedPref: SharedPreferences

    private lateinit var presenter: KajianLocationPresenter
    private var listReport = mutableListOf<Locations>()
    lateinit var adapterList: KajianLocationListAdapter
    var latitude: String? = null
    var longitude: String? = null
    var id: Int = 0


    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_astidz)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Lokasi Kajian"

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        latitude = sharedPref.getString("LATITUDE", null)
        longitude = sharedPref.getString("LONGITUDE", null)

        presenter = KajianLocationPresenter(this, this)
        id = sharedPref.getInt("ID_USER",0 )
        if(longitude!=null)presenter.kajianLocation(latitude!!, longitude!!)
        else presenter.allKajianLocation()
        initViewRecyclerView()

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


    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    private fun initViewRecyclerView(){

        adapterList = KajianLocationListAdapter(
            listReport,
            onClickListener = { item ->
                var intent = Intent(this, KajianActivity::class.java)
                intent.putExtra("type", KajianType.Lokasi.value)
                intent.putExtra("location", item.nama)
                intent.putExtra("location_id", item.id)
                startActivity(intent)
            }
        )
        rv_asatidz.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        rv_asatidz.layoutManager = LinearLayoutManager(this)
        rv_asatidz.adapter = adapterList

    }


    override fun showLocation(list: List<Locations>) {
        swipe_layout.isRefreshing = false
        Log.d("KajianActivity", "test $list")
        this.listReport.clear()
        this.listReport.addAll(list)

        adapterList.notifyDataSetChanged()
        view_no_data.visibility = if (listReport.isEmpty()) View.VISIBLE else View.GONE
        swipe_layout.visibility = if (listReport.isNotEmpty()) View.VISIBLE else View.GONE

    }



}