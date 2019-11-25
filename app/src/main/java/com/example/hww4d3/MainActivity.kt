package com.example.hww4d3

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {

    private lateinit var fragmentleft: LeftFragment
    private lateinit var fragmentright: RightFragment
    var value = Math.random()
    private val requestCode = 777
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf("android.permission.ACCESS_FINE_LOCATION"), requestCode)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf("android.permission.ACCESS_FINE_LOCATION"),
                    requestCode
                )
            }
        } else {
            Log.d("TAG_X", "Permission granted")
        }
        open_settings.setOnClickListener {
            val intent = Intent("android.settings.APPLICATION_SETTINGS")
            startActivity(intent)
        }



        fragmentleft = LeftFragment()
        fragmentright = RightFragment()

        random_button.setOnClickListener {

            newNumber()
            if(count == 0) {
                openFragments()
            }

        }

    }



    private fun openFragments() {
        supportFragmentManager.beginTransaction()
            .add(R.id.left_frag, fragmentleft )
            .addToBackStack(fragmentleft.tag)
            .commit()

        supportFragmentManager.beginTransaction()
            .replace(R.id.right_frag, fragmentright)
            .addToBackStack(fragmentright.tag)
            .commit()

        count+=1
    }


    override fun onBackPressed() {
        super.onBackPressed()

        supportFragmentManager.popBackStack()
        count -= 1
    }

    private fun newNumber(){

        value = Math.random()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == this.requestCode && permissions[0] == "android.permission.ACCESS_FINE_LOCATION" && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Thank you for granting the permission.", Toast.LENGTH_SHORT)
                .show()


        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    "android.permission.ACCESS_FINE_LOCATION"
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf("android.permission.ACCESS_FINE_LOCATION"),
                    requestCode
                )
            } else {
                Toast.makeText(
                    this,
                    "You need to allow permissions to use this application.",
                    Toast.LENGTH_SHORT
                ).show()
                random_button.visibility = View.INVISIBLE

                textview_main.text = "You need to allow location permissions to use this application."
                open_settings.visibility = View.VISIBLE

            }

        }
    }




}