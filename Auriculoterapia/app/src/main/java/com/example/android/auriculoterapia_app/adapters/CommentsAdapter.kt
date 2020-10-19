package com.example.android.auriculoterapia_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.models.helpers.CommentResponse

class CommentsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var comments: ArrayList<CommentResponse> = ArrayList()

    inner class CommentViewHolder constructor(var view: View):RecyclerView.ViewHolder(view){

        var sesion = view.findViewById<TextView>(R.id.tvCommentSesion)
        var descripcion = view.findViewById<TextView>(R.id.tvcommentDescription)
        val sesionname = "Sesion "

        fun bind(comment: CommentResponse){
            sesion.text = "$sesionname${comment.sesion.toString()}"
            descripcion.text = comment.descripcion
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CommentViewHolder ->{
                val comment = comments.get(position)
                holder.bind(comment)
            }
        }
    }

    fun submitList(comments: ArrayList<CommentResponse>){
        this.comments = comments
    }


}