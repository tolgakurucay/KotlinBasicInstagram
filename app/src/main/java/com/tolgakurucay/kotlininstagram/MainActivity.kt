package com.tolgakurucay.kotlininstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tolgakurucay.kotlininstagram.databinding.ActivityMainBinding

private lateinit var binding:ActivityMainBinding
private lateinit var auth:FirebaseAuth
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        val view=binding.root

        setContentView(view)
    auth= Firebase.auth
        var currentUser=auth.currentUser
        if(currentUser!=null)
        {
            val intent=Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun signInClick(view: View)
    {
        val email= binding.editTextTextEmailAddress.text.toString()
        val password=binding.editTextTextPassword.text.toString()
        if(email.isNotEmpty() &&password.isNotEmpty())
        {
           auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
               val intent= Intent(this@MainActivity,FeedActivity::class.java)

               startActivity(intent)
               finish()
           }
               .addOnFailureListener {
                   Toast.makeText(this@MainActivity,"Böyle bir hesap bulunamadı",Toast.LENGTH_LONG).show()
               }
        }
        else
        {
            Toast.makeText(this,"E-posta ve şifre alanlarını doldur",Toast.LENGTH_SHORT).show()
        }

    }
    fun signUpClick(view:View)
    {
        val email= binding.editTextTextEmailAddress.text.toString()
        val password=binding.editTextTextPassword.text.toString()
        if(email.isNotEmpty() &&password.isNotEmpty())

        {
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                //success
                val intent= Intent(this@MainActivity,FeedActivity::class.java)

                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity,"Bu e-postaya kayıtlı hesap zaten var",Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(this,"E-posta ve şifre alanlarını doldur",Toast.LENGTH_SHORT).show()
        }



    }


}