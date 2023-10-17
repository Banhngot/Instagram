package com.neatroots.instagram.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.neatroots.instagram.HomeActivity
import com.neatroots.instagram.Models.Reel
import com.neatroots.instagram.Ultils.REEL
import com.neatroots.instagram.Ultils.REEL_FOLDER
import com.neatroots.instagram.Ultils.uploadVideo
import com.neatroots.instagram.databinding.ActivityReelBinding

class ReelActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityReelBinding.inflate(layoutInflater)
    }
    private lateinit var videoUrl:String
    lateinit var progressDialog:ProgressDialog
    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER,progressDialog){
                    url ->
                if(url!=null){

                    videoUrl=url

                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        progressDialog= ProgressDialog(this)

        binding.selectReel.setOnClickListener{
            launcher.launch("video/*")
        }

        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@ReelActivity, HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            val reel: Reel = Reel(videoUrl!!,binding.caption.editText?.text.toString())

            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + REEL).document().set(reel).addOnSuccessListener {
                    startActivity(Intent(this@ReelActivity,HomeActivity::class.java))
                    finish()
                }

            }
        }
    }
}