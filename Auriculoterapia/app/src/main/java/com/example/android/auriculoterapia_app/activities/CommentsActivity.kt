package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.CommentsAdapter
import com.example.android.auriculoterapia_app.models.helpers.CommentResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommentsActivity : AppCompatActivity() {

    lateinit var commentsRecycler: RecyclerView
    lateinit var comments:ArrayList<CommentResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        val actionBar = supportActionBar
        actionBar!!.title = "Comentarios"


        commentsRecycler = findViewById(R.id.commentsRecycler)
        commentsRecycler.layoutManager = LinearLayoutManager(this)

        val commentsAdapter = CommentsAdapter()
        commentsRecycler.adapter = commentsAdapter

        comments = Gson().fromJson(
            intent.getStringExtra("comments"),
            object : TypeToken<ArrayList<CommentResponse?>?>() {}.type
        )

        commentsAdapter.submitList(comments)
    }
}