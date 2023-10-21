package com.neatroots.instagram.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.neatroots.instagram.HomeActivity
import com.neatroots.instagram.Models.Post
import com.neatroots.instagram.Models.User
import com.neatroots.instagram.Ultils.POST
import com.neatroots.instagram.Ultils.POST_FOLDER
import com.neatroots.instagram.Ultils.USER_NODE
import com.neatroots.instagram.Ultils.USER_PROFILE_FOLDER
import com.neatroots.instagram.Ultils.uploadImage


import com.neatroots.instagram.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageUrl:String?=null
    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
            uri->
        uri?.let {
            uploadImage(uri, POST_FOLDER){
                url ->
                if(url!=null){
                     binding.selectImage.setImageURI(uri)

                    imageUrl=url

                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }


        binding.selectImage.setOnClickListener{
            launcher.launch("image/*")
        }

        binding.cancelButton.setOnClickListener{
            startActivity(Intent(this@PostActivity,HomeActivity::class.java))
            finish()
        }

        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document().get().addOnSuccessListener {


                var user=it.toObject<User>()!!
                val post:Post= Post(postUrl = imageUrl!!, caption = binding.caption.editText?.text.toString(), name=Firebase.auth.currentUser!!.uid,time=System.currentTimeMillis().toString())



            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document().set(post).addOnSuccessListener {
                    startActivity(Intent(this@PostActivity,HomeActivity::class.java))
                    finish()
                }

            }
        }
    }
}
}