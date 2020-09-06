package com.example.android.auriculoterapia_app.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import com.example.android.auriculoterapia_app.R
import kotlinx.android.synthetic.main.activity_initial_image.*
import com.cloudinary.*
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.android.auriculoterapia_app.constants.ConfigCloudinary
import com.example.android.auriculoterapia_app.fragments.patient.NewTreatmentFragment


class InitialImageActivity : AppCompatActivity() {
    lateinit var filepath : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial_image)

        val config = ConfigCloudinary.config()

        pickImageFromGallery()

        //MediaManager.init(this,config)


        btn_InitialImageSave.setOnClickListener {
            //uploadToCloudinary(filepath)
            val intent = Intent()
            intent.putExtra("filepath",filepath)
            setResult(12,intent)
            finish()
        }

    }

    companion object{
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type ="image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        filepath = this!!.getRealPathFromUri(data?.data!!,this)!!
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            iv_initialImage.setImageURI(data?.data)

        }
    }

    private fun getRealPathFromUri(imageUri : Uri,activity:Activity): String? {
        val cursor = activity.contentResolver.query(imageUri,null,null,null,null)

        if(cursor == null){
            return imageUri.path;
        }else{
            cursor.moveToFirst()
            //val idx = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            //val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            return cursor.getString(idx);
        }
    }

    
}