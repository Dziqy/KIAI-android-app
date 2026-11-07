package com.im.kiai.ui.following

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
import com.im.kiai.model.Kajian
import com.im.kiai.network.message.response.Asatidz
import com.im.kiai.ui.astidz.AsatidzListAdapter
import com.im.kiai.ui.astidz.AsatidzPresenter
import com.im.kiai.ui.kajian.KajianActivity
import com.im.kiai.utils.enum.KajianType
import kotlinx.android.synthetic.main.activity_astidz.*


class FollowingActivity : AppCompatActivity(), FollowingView{
    private lateinit var sharedPref: SharedPreferences

    private lateinit var presenter: FollowingPresenter
    private var listReport = mutableListOf<Asatidz>()
    lateinit var adapterList: FollowingListAdapter
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
        toolbar.title = "Mengikuti"

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        presenter = FollowingPresenter(this, this)
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

        adapterList = FollowingListAdapter(
            listReport,
            onClickListener = { item ->
                var intent = Intent(this, KajianActivity::class.java)
                intent.putExtra("type", KajianType.Asatidz.value)
                intent.putExtra("asatidz", item.name)
                intent.putExtra("asatidz_id", item.id)
                startActivity(intent)
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