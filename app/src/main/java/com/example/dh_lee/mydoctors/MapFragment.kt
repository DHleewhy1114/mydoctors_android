package com.example.dh_lee.mydoctors

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nhn.android.maps.NMapContext
import com.nhn.android.maps.NMapView

class MapFragment : Fragment() {
    private var mMapContext: NMapContext? = null
    private val CLIENT_ID = "aTbUWhjsFEFzfr6Z1G1B"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        mMapContext = NMapContext(super.getActivity());
        mMapContext?.onCreate()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.map_fragment,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val mapView:NMapView = getView()!!.findViewById(R.id.mapView)
                //findViewById<NMapView>(R.id.mapView);
        mapView.setClientId(CLIENT_ID)
        mMapContext?.setupMapView(mapView)
    }
    override fun onStart(){
        super.onStart();
        mMapContext?.onStart();
    }
    override fun onResume() {
        super.onResume();
        mMapContext?.onResume();
    }
    override fun onPause() {
        super.onPause();
        mMapContext?.onPause();
    }
    override fun onStop() {
        mMapContext?.onStop();
        super.onStop();
    }
    override fun onDestroyView() {
        super.onDestroyView();
    }
    override fun onDestroy() {
        mMapContext?.onDestroy();
        super.onDestroy();
    }
}
/*
* public class Fragment1 extends Fragment {
    private NMapContext mMapContext;
    private static final String CLIENT_ID = "YOUR_CLIENT_ID";// 애플리케이션 클라이언트 아이디 값
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment1, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NMapView mapView = (NMapView)getView().findViewById(R.id.mapView);
        mapView.setClientId(CLIENT_ID)
        mMapContext.setupMapView(mapView)
    }
    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }
}
*/