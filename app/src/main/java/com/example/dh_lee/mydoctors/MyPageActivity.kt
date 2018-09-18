package com.example.dh_lee.mydoctors


import android.app.Activity
import android.app.PendingIntent.getActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import java.net.URI
import android.graphics.Bitmap
import android.graphics.Path
import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Blob
import com.github.kittinunf.fuel.core.DataPart
import com.github.kittinunf.fuel.core.interceptors.loggingResponseInterceptor
import okhttp3.MultipartBody
import java.io.File


class MyPageActivity : AppCompatActivity() {
    val RESULT_LOAD_IMAGE = 1
    lateinit var img: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        img = findViewById(R.id.profile_image_view)
        img.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, RESULT_LOAD_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage: Uri = data.data
            val cursor = contentResolver.query(selectedImage, null, null, null, null)
            cursor!!.moveToNext()
            val path = cursor.getString(cursor.getColumnIndex("_data"))
            val imgfile = File(path)
            if (imgfile.exists()) {
                Log.e("file name", imgfile.absolutePath)
                val myBitmap = BitmapFactory.decodeFile(imgfile.absolutePath)
                img.setImageBitmap(myBitmap)
            }
            Fuel.upload("http://10.0.2.2:5000/user_profile").dataParts{ request, url ->
                listOf(DataPart(imgfile,"file"))
            }.responseString { request, response, result ->
                //Log.e("request",request.toString())
                //Log.e("sended",response.toString())
            }
        }
    }
}
