package com.im.kiai.ui.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.im.kiai.R
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.postDelayed
import androidx.preference.PreferenceManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.im.kiai.BuildConfig
import com.im.kiai.model.Kajian
import com.im.kiai.ui.about.AboutActivity
import com.im.kiai.ui.astidz.AstidzActivity
import com.im.kiai.ui.following.FollowingActivity
import com.im.kiai.ui.kajian.KajianActivity
import com.im.kiai.ui.location.KajianLocationActivity
import com.im.kiai.ui.location.NearestLocationActivity
import com.im.kiai.ui.login.LoginActivity
import com.im.kiai.ui.profile.ProfileActivity
import com.im.kiai.utils.enum.KajianType
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.main_content

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var presenter: MainPresenter

    lateinit var viewPagerAdapter: ImageSlideAdapter
    private  var myLocation: Location? = null

    var retryGetLocCount: Int = 3
    private var mFusedLocationClient: FusedLocationProviderClient? = null


    companion object {
        const val REQUEST_PERMISSIONS_REQUEST_CODE = 1
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        checkSignedInOrNot()

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        presenter =  MainPresenter(this, this)
        presenter.latestKajian()

        btn_kajian_terdekat.setOnClickListener {
            var intent = Intent(this, KajianActivity::class.java)
            intent.putExtra("type", KajianType.JadwalTerdekat.value)
            startActivity(intent)
        }
        btn_lokasi_terdekat.setOnClickListener {
            var intent = Intent(this, NearestLocationActivity::class.java)
            startActivity(intent)
        }

        btn_kajian_online.setOnClickListener {
            var intent = Intent(this, KajianActivity::class.java)
            intent.putExtra("type", KajianType.Online.value)
            startActivity(intent)
        }

        btn_kajian_muslimah.setOnClickListener {
            var intent = Intent(this, KajianActivity::class.java)
            intent.putExtra("type", KajianType.Muslimah.value)
            startActivity(intent)
        }

        btn_astidz.setOnClickListener {
            var intent = Intent(this, AstidzActivity::class.java)
            startActivity(intent)
        }

        btn_lokasi_kajian.setOnClickListener {
            var intent = Intent(this, KajianLocationActivity::class.java)
            startActivity(intent)
        }

        btn_tentang.setOnClickListener {
            var intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.search -> {
                    var intent = Intent(this, KajianActivity::class.java)
                    intent.putExtra("type", KajianType.Cari.value)
                    startActivity(intent)
                }
                R.id.following -> {
                    var intent = Intent(this, FollowingActivity::class.java)
                    startActivity(intent)
                }
                R.id.account -> {
                    var intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }


    public override fun onStart() {
        super.onStart()
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    private fun checkSignedInOrNot(){
        val islogin = sharedPref.getBoolean("IS_LOGIN", false)
        if(!islogin){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val username = sharedPref.getString("NAMA_USER", "")
            tv_user_name.text = username
        }

    }

    override fun setLatestKajian(data: List<Kajian>) {
        viewPagerAdapter = ImageSlideAdapter(this, data)
        viewpager.adapter = viewPagerAdapter
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    // -----  Get Current Location  -------

    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }

    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")

            showSnackbar("Cari Cukur butuh lokasi anda untuk mencari tempat cukur terdekat",
                View.OnClickListener {
                    // Request permission
                    startLocationPermissionRequest()
                })

        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest()
        }
    }
    private fun showSnackbar(message: String,
                             listener: View.OnClickListener) {
        Snackbar.make(main_content, message,
            Snackbar.LENGTH_LONG).setAction("OK", listener)
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.size <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation()
            } else {
                showSnackbar("Cari Cukur butuh lokasi anda untuk mencari tempat cukur terdekat",
                    View.OnClickListener {
                        // Build intent that displays the App settings screen.
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts(
                            "package",
                            BuildConfig.APPLICATION_ID, null
                        )
                        intent.data = uri
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    })
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    myLocation = task.result
                    sharedPref.edit().putString("LATITUDE", task.result.latitude.toString() ).apply()
                    sharedPref.edit().putString("LONGITUDE", task.result.longitude.toString()).apply()

                } else {
                    Log.d(TAG, "message failed get location ${task.exception?.message}")
                    if(retryGetLocCount>0){
                        retryGetLocCount--
                        val handler = Handler(mainLooper)
                        handler.postDelayed(2000L) {
                            getLastLocation()
                        }
                        Log.d(TAG, "retry $retryGetLocCount")
                    }else{
                        Log.d(TAG, "getLastLocation:exception ${task.exception}")
//                        showMessage("Lokasi anda tidak bisa terdeteksi")
                    }

                }
            }

            .addOnFailureListener {
                Log.d(TAG, "getLastLocation:exception ${it.message}")

            }

    }

}