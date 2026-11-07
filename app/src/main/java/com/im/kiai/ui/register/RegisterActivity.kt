package com.im.kiai.ui.register

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.im.kiai.R
import com.im.kiai.network.message.request.RegisterRequest
import com.im.kiai.ui.login.LoginPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterView {
    private lateinit var sharedPref: SharedPreferences

    private lateinit var presenter: RegisterPresenter
    companion object{
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter =  RegisterPresenter(this, this)
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)

        toolbar.title = "Daftar"

        val previewRequest =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
//                    if(it.data?.getBooleanExtra("refresh", false) == true && role == "ADMIN") presenter.getAllReport()
//                    else if(it.data?.getBooleanExtra("refresh", false) == true) presenter.getReport(rt.toInt(), rw.toInt())
                }
            }

        btn_submit.setOnClickListener {
            var name = edt_name.text.toString()
            var phone = edt_phone.text.toString()
            var gender = if(rb_male.isChecked) "Laki - laki" else if(rb_female.isChecked) "Perempuan" else ""
            var email = edt_email.text.toString()
            var password = edt_password.text.toString()
            var password2 = edt_password_2.text.toString()
            if(name.isEmpty() || gender.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || password2.isEmpty()){
                showMessage("Data belum lengkap")
            }else if(password!=password2){
                showMessage("Password tidak sama")
            }else{
                val data = RegisterRequest(
                    id = null,
                    name = name,
                    gender = gender,
                    phone = phone,
                    email = email,
                    password = password
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
        Toast.makeText(this, "Pendaftaran berhasil. Silahkan login menggunakan akun baru anda", Toast.LENGTH_LONG).show()
        finish()
    }


    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


}