package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity

class TakePictureActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE:Int = 1;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_picture)
        dispatchTakePictureIntent()
    }

       fun dispatchTakePictureIntent() {
            val takePictureIntent:Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

}
