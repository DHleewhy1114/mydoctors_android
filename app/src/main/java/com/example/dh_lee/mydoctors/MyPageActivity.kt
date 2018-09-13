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
import java.io.File


class MyPageActivity : AppCompatActivity() {
    val RESULT_LOAD_IMAGE=1
    lateinit var img:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        img = findViewById(R.id.profile_image_view)
        img.setOnClickListener{
                val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, RESULT_LOAD_IMAGE)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
        val selectedImage: Uri = data.getData();
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            Log.e("file name", selectedImage.path)
            Log.e("file name", selectedImage.lastPathSegment)
    }
    }
}
