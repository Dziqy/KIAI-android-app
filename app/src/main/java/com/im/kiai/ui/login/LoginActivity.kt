package com.im.kiai.ui.login

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.im.kiai.ui.home.MainActivity
import com.im.kiai.R
import com.im.kiai.model.User
import com.im.kiai.ui.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var presenter: LoginPresenter
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        presenter =  LoginPresenter(this, this)


        btn_login.setOnClickListener {
            val email = edt_email.text.toString()
            val pswrd = edt_password.text.toString()
            if(email.isNullOrEmpty() || pswrd.isNullOrEmpty()){
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_LONG).show()
            } else presenter.login(email, pswrd)
        }

        tv_register.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun setData(data: User) {

        sharedPref.edit().putBoolean("IS_LOGIN", true).apply()
        sharedPref.edit().putInt("ID_USER", data.id).apply()
        sharedPref.edit().putString("NAMA_USER", data.name).apply()
        sharedPref.edit().putString("EMAIL_USER", data.email).apply()
        sharedPref.edit().putString("PHONE", data.phone).apply()
        sharedPref.edit().putString("GENDER", data.gender).apply()

        Log.d("LoginActivity", "LOGIN BERHASIL $data")

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }
}