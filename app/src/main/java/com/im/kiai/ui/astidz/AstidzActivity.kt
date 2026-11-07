package com.im.kiai.ui.astidz

import android.content.Intent
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.activity_astidz.*


class AstidzActivity : AppCompatActivity(), AsatidzView{
    private lateinit var sharedPref: SharedPreferences

    private lateinit var presenter: AsatidzPresenter
    private var listReport = mutableListOf<Asatidz>()
    lateinit var adapterList: AsatidzListAdapter
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
        toolbar.title = "Profil Asatidz"

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        presenter = AsatidzPresenter(this, this)
        id = sharedPref.getInt("ID_USER",0 )
        presenter.asatidz(id)
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

        adapterList = AsatidzListAdapter(
            listReport,
            onClickFollow = { item ->
                Log.d("AsatidzActivity", "clck $item")
                presenter.follow(asatidzId = item.id, id = id )
            },
            onClickUnfollow = { item ->
                Log.d("AsatidzActivity", "clck2 $item")
                presenter.unfollow(asatidzId = item.id, id = id )

            }
        )
        rv_asatidz.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        rv_asatidz.layoutManager = LinearLayoutManager(this)
        rv_asatidz.adapter = adapterList

    }


    override fun showAsatidz(list: List<Asatidz>) {
        swipe_layout.isRefreshing = false
        Log.d("KajianActivity", "test $list")
        this.listReport.clear()
        this.listReport.addAll(list)

        adapterList.notifyDataSetChanged()
        view_no_data.visibility = if (listReport.isEmpty()) View.VISIBLE else View.GONE
        swipe_layout.visibility = if (listReport.isNotEmpty()) View.VISIBLE else View.GONE

    }



}