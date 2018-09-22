package com.android.flickapp.screen.splash

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.android.flickapp.R
import com.android.flickapp.manager.ConnectionManager
import com.android.flickapp.screen.imageSearch.ImageSearchActivity
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton

class SplashActivity : AppCompatActivity() {

    private lateinit var mContext : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mContext = this

    }


    override fun onResume() {
        super.onResume()


        // checks where internet connection is available
        if (!ConnectionManager.isConnected(mContext.applicationContext)) {
            alert(getString(R.string.dialog_internet_desc), getString(R.string.dialog_internet_title)) {
                okButton { onResume() }
            }.show()
            return
        }

        Handler().postDelayed({

            finish()
            startActivity(Intent(this,ImageSearchActivity::class.java))
        }, 3000)

    }
}
