package com.example.dh_lee.mydoctors

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nhn.android.maps.NMapCompassManager
import com.nhn.android.maps.NMapLocationManager
import com.nhn.android.mapviewer.overlay.NMapOverlayManager
import com.nhn.android.mapviewer.overlay.NMapResourceProvider


//https://developers.naver.com/docs/map/android/
class FindByHospitalActivity : FragmentActivity() {
    val lists:ArrayList<HospitalData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_by_hospital)
        /*val fragment = MapFragment()
        fragment.setArguments(Bundle())
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.add(R.id.fragmentHere, fragment)
        fragmentTransaction.commit()*/
        addlists()
        val recyclehospital:RecyclerView=findViewById(R.id.doctor_horizon)
        recyclehospital.layoutManager = LinearLayoutManager(this)
        recyclehospital.layoutManager = GridLayoutManager(this,2)
        recyclehospital.adapter = HospitalAdapter(lists, this,{ item:HospitalData -> itemClicked(item) })
    }
    private fun itemClicked(item : HospitalData) {
    }
    fun addlists(){
        val a = HospitalData("ㅆㅆ병원","주소주소주소1")
        val b = HospitalData("ㅇㅇ병원","주소주소주소2")
        val c = HospitalData("ㅃㅃ병원","주소주소주소3")
        lists.add(a)
        lists.add(b)
        lists.add(c)
        lists.add(a)
        lists.add(b)
        lists.add(c)
    }

}
