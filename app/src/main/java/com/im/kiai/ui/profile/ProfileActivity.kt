package com.im.kiai.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.im.kiai.R
import android.content.SharedPreferences
import android.view.MenuItem
import androidx.preference.PreferenceManager
import com.im.kiai.ui.login.LoginActivity
import com.im.kiai.ui.profile.ubahpassword.UbahPasswordActivity
import com.im.kiai.ui.profile.ubahprofil.UbahProfilActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.toolbar
import kotlinx.android.synthetic.main.activity_register.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        tv_logout.setOnClickListener {

            sharedPref.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }


        tv_ubah_password.setOnClickListener {
            var intent=Intent(this, UbahPasswordActivity::class.java)
            startActivity(intent)
        }
        tv_ubah_profile.setOnClickListener {
            var intent=Intent(this, UbahProfilActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        val username = sharedPref.getString("NAMA_USER", "")
        val email = sharedPref.getString("EMAIL_USER", "")
        tv_user_name.text = username
        tv_email.text = email

    }

    override fun onDestroy() {
        super.onDestroy()
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

}