package com.neatroots.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.neatroots.instagram.Models.User
import com.neatroots.instagram.Ultils.USER_NODE
import com.neatroots.instagram.Ultils.USER_PROFILE_FOLDER
import com.neatroots.instagram.Ultils.uploadImage
import com.neatroots.instagram.databinding.ActivitySiginUpBinding

class SiginUpActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySiginUpBinding.inflate(layoutInflater)
    }
    lateinit var user: User
    private val launcher= registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){
                if(it==null){

                }else{
                    user.image=it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = User()
        binding.signUpBtn.setOnClickListener{
            if(binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
                ){
                Toast.makeText(this@SiginUpActivity, "Please fill all the Information",Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener{
                    result->

                    if(result.isSuccessful){
                        user.name=binding.name.editText?.text.toString()
                        user.password=binding.password.editText?.text.toString()
                        user.email=binding.email.editText?.text.toString()
                        Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).set(user).addOnSuccessListener {
                            Toast.makeText(this@SiginUpActivity,"Login",Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@SiginUpActivity,result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        binding.addImage.setOnClickListener{
            launcher.launch("image/*")
        }
    }
}