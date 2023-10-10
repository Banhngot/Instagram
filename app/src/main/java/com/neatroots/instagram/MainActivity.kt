package com.neatroots.instagram

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor= Color.TRANSPARENT

        Handler(Looper.getMainLooper()).postDelayed({
            if(FirebaseAuth.getInstance().currentUser==null)
            startActivity(Intent(this,SiginUpActivity::class.java))
            else
                startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }, 3000)
    }
}