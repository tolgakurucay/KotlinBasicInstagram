package com.tolgakurucay.kotlininstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.tolgakurucay.kotlininstagram.databinding.ActivityFeedBinding

private lateinit var binding: ActivityFeedBinding
private lateinit var auth: FirebaseAuth
private lateinit var db:FirebaseFirestore
private lateinit var postAdapter: PostAdapter

private lateinit var postArrayList:ArrayList<Post>

class FeedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFeedBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        postArrayList=ArrayList<Post>()
        getData()

        binding.recyclerView.layoutManager=LinearLayoutManager(this)
        postAdapter= PostAdapter(postArrayList)
        binding.recyclerView.adapter= postAdapter



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.add_post)
        {
            val intent= Intent(this,UploadActivity::class.java)

            startActivity(intent)
        }
        else if (item.itemId==R.id.signout)
        {
            auth.signOut()
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getData()
    {
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if(error!=null)
            {
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }
            else
            {
                if(value!=null)
                {
                    if(!value.isEmpty)
                    {
                        val documents=value.documents
                        postArrayList.clear()
                        for(document in documents)
                        {
                            val comment=document.get("comment") as String
                            val userEmail=document.get("userEmail") as String
                            val date=document.get("date")
                            val downloadUrl=document.get("downloadUrl") as String
                            val post=Post(userEmail,comment,downloadUrl)
                            postArrayList.add(post)


                        }
                        postAdapter.notifyDataSetChanged()
                    }
                }
                else
                {
                    Toast.makeText(this,"There's no data",Toast.LENGTH_LONG).show()
                }
            }




        }
    }


}