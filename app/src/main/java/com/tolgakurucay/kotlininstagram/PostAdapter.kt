package com.tolgakurucay.kotlininstagram

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import com.tolgakurucay.kotlininstagram.databinding.RecyclerRowBinding

class PostAdapter(val postArray:ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.Postholder>() {

    class Postholder(val binding:RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Postholder {
        val binding=RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Postholder(binding)
    }

    override fun onBindViewHolder(holder: Postholder, position: Int) {
        holder.binding.recyclerEmailText.text=postArray.get(position).email
        holder.binding.recyclerCommentText.text=postArray.get(position).comment
        Picasso.get().load(postArray.get(position).downloadUrl).into(holder.binding.recyclerImageView)

    }

    override fun getItemCount(): Int {
       return postArray.size
    }

}