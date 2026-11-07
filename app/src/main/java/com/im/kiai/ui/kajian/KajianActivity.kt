package com.im.kiai.ui.kajian

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.im.kiai.R
import com.im.kiai.model.Kajian
import com.im.kiai.utils.enum.KajianType
import com.im.lapor.ui.headofrt.report.KajianView
import kotlinx.android.synthetic.main.activity_kajian.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class KajianActivity : AppCompatActivity(), KajianView {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var presenter: KajianPresenter
    private lateinit var type: String
    var latitude: String? = null
    var longitude: String? = null

    lateinit var adapterList: KajianListAdapter
    private var listReport = mutableListOf<Kajian>()
    private var listReportMaster = mutableListOf<Kajian>()
    var asatidzId: Int = 0
    var locationId: Int = 0


    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kajian)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        latitude = sharedPref.getString("LATITUDE", null)
        longitude = sharedPref.getString("LONGITUDE", null)

        type = intent.getStringExtra("type") ?: ""
        if(type == KajianType.Asatidz.value){
            var asatidz = intent.getStringExtra("asatidz") ?: ""
            asatidzId = intent.getIntExtra("asatidz_id", 0)
            toolbar.title = asatidz
        }
        else if(type == KajianType.Lokasi.value){
            var asatidz = intent.getStringExtra("location") ?: ""
            locationId = intent.getIntExtra("location_id", 0)
            toolbar.title = asatidz
        }
        else toolbar.title = type

        presenter = KajianPresenter(this, this)

        val previewRequest =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
//                    if(it.data?.getBooleanExtra("refresh", false) == true && role == "ADMIN") presenter.getAllReport()
//                    else if(it.data?.getBooleanExtra("refresh", false) == true) presenter.getReport(rt.toInt(), rw.toInt())
                }
            }

        adapterList = KajianListAdapter(
            listReport,
            clickListener = {item ->
                Log.d("KajianActivity", "test data $item");
                val intent = Intent(this, DetailKajianActivity::class.java)
                intent.putExtra("data", item)
//                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                previewRequest.launch(intent)
            }
        )

        initViewRecyclerView()
        if(type == KajianType.Asatidz.value) presenter.kajianAsatidz(asatidzId)
        else if(type == KajianType.Lokasi.value) presenter.kajianLocation(locationId)
        else if(latitude==null || type == KajianType.Cari.value || type == KajianType.Online.value|| type == KajianType.Muslimah.value) presenter.kajian()
        else presenter.kajianNearest(latitude!!, longitude!!)


        swipe_layout.setOnRefreshListener {
            if(type == KajianType.Asatidz.value) presenter.kajianAsatidz(asatidzId)
            else if(type == KajianType.Lokasi.value) presenter.kajianLocation(locationId)
            else if(latitude==null || type == KajianType.Cari.value) presenter.kajian()
            else presenter.kajianNearest(latitude!!, longitude!!)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipe_layout.setColorSchemeColors(getColor(R.color.blue), getColor(R.color.teal_700))
        }


        search_bar.visibility = if(type == KajianType.Cari.value)  View.VISIBLE else View.GONE

        et_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                search(keyword = et_search.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

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

    private fun initViewRecyclerView(){
        rv_report.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        rv_report.layoutManager = LinearLayoutManager(this)
        rv_report.adapter = adapterList

    }

    public override fun onStart() {
        super.onStart()

    }

    public override fun onDestroy() {
        presenter.onViewDestroyed()
        super.onDestroy()
    }
    
    override fun showMessage(message: String) {
        swipe_layout.isRefreshing = false
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showKajian(list: List<Kajian>) {
        swipe_layout.isRefreshing = false
        Log.d("KajianActivity", "test $list")
        this.listReport.clear()
        if(type == KajianType.JadwalTerdekat.value){

            var sorterList = sortNearestTime(list)
            this.listReport.addAll(sorterList)
        }else if(type == KajianType.Muslimah.value){
            this.listReport.addAll(list.filter { it.jamaah == "Akhwat" })
        }else if(type == KajianType.Online.value){
            this.listReport.addAll(list.filter { it.streaming != null })
        }else if(type == KajianType.Cari.value){
            this.listReportMaster.addAll(list)
        }else{
            this.listReport.addAll(list)
        }

        adapterList.notifyDataSetChanged()
        view_no_data.visibility = if (listReport.isEmpty()) View.VISIBLE else View.GONE
        swipe_layout.visibility = if (listReport.isNotEmpty()) View.VISIBLE else View.GONE
    }

    fun search(keyword: String){
        var result = listReportMaster.filter {
            it.judul.contains(keyword, true) || it.nama.contains(keyword, true) || it.tanggal.contains(keyword, true) || (it.asatidz?:"").contains(keyword, true)
        }
        listReport.clear()
        listReport.addAll(result)
        adapterList.notifyDataSetChanged()
        view_no_data.visibility = if (listReport.isEmpty()) View.VISIBLE else View.GONE
        swipe_layout.visibility = if (listReport.isNotEmpty()) View.VISIBLE else View.GONE
    }

    fun sortNearestTime(list: List<Kajian>): List<Kajian>{
        var listTop = list.filter {
            var date = getDateFormat("${it.tanggal} ${it.waktu}")
            date.after(Date())
        }
//        var listBottom = list.filter {
//            var date = getDateFormat("${it.tanggal} ${it.waktu}")
//            date.before(Date())
//        }
//        var listFinal = mutableListOf<Kajian>()
//        listFinal.addAll(listTop)
//        listFinal.addAll(listBottom)
        return listTop
    }

    private fun getDateFormat(string: String): Date {
        val inputSDF = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        var date: Date = try {
            //here you get Date object from string
            print("parse $string")
            inputSDF.parse(string)
        } catch (e: ParseException) {
            print("error ${e.message}")
            Log.d("MainActivity", "error ${e.message}")
            return Date()
        }

        return date
    }


}