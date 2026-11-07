package com.im.kiai.ui.profile.ubahprofil

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.im.kiai.R
import com.im.kiai.network.message.request.RegisterRequest
import kotlinx.android.synthetic.main.activity_register.*

class UbahProfilActivity : AppCompatActivity(), UbahProfilView {
    private lateinit var sharedPref: SharedPreferences
    var iduser : Int = 0
    lateinit var username: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var gender: String

    private lateinit var presenter: UbahProfilPresenter
    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubah_profil)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = "Ubah Profil"

        presenter =  UbahProfilPresenter(this, this)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        iduser = sharedPref.getInt("ID_USER", 0)
        var spusername = sharedPref.getString("NAMA_USER", "") ?: ""
        var spemail = sharedPref.getString("EMAIL_USER", "") ?: ""
        var spphone = sharedPref.getString("PHONE", "") ?: ""
        var spgender = sharedPref.getString("GENDER", "") ?: ""

        edt_name.setText(spusername)
        edt_phone.setText(spphone)
        edt_email.setText(spemail)
        if(spgender ==  "Laki - laki"){
            rb_male.isChecked = true
        }else if(spgender ==  "Perempuan"){
            rb_female.isChecked = true
        }

        btn_submit.setOnClickListener {
             username = edt_name.text.toString()
             phone = edt_phone.text.toString()
             gender = if(rb_male.isChecked) "Laki - laki" else if(rb_female.isChecked) "Perempuan" else ""
             email = edt_email.text.toString()
            if(username.isEmpty() || gender.isEmpty() || email.isEmpty() || phone.isEmpty()){
                showMessage("Data belum lengkap")
            }else{
                val data = RegisterRequest(
                    id = iduser,
                    name = username,
                    gender = gender,
                    phone = phone,
                    email = email,
                    password = ""
                )
                presenter.register(data)
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

    override fun registerSuccess() {
        sharedPref.edit().putString("NAMA_USER", username).apply()
        sharedPref.edit().putString("EMAIL_USER", email).apply()
        sharedPref.edit().putString("PHONE", phone).apply()
        sharedPref.edit().putString("GENDER", gender).apply()
        Toast.makeText(this, "Profil berhasil diubah", Toast.LENGTH_LONG).show()
        finish()
    }


    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}