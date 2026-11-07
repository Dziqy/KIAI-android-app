package com.im.kiai.ui.about

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.im.kiai.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences

    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        toolbar.title = "Tentang KIAI"


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



}