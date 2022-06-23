package com.nrsoft.animalhospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nrsoft.animalhospital.databinding.ActivityItemBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ItemActivity extends AppCompatActivity {

    ActivityItemBinding binding;

    String name;
    String addr;
    String tel;

    double lat;
    double lng;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= ActivityItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FragmentManager manager= getSupportFragmentManager();
        mapFragment= (SupportMapFragment) manager.findFragmentById(R.id.frag_map);


        //텍스트뷰 영역
        Intent intent= getIntent();
        name= intent.getStringExtra("name");
        addr= intent.getStringExtra("addr");
        tel= intent.getStringExtra("tel");

        //TextView에 아이템들 넣기
        binding.tvName.setText("병원명 : "+name);
        binding.tvAddr1.setText("도로명 주소: "+addr);
        binding.tvTel.setText("tel : "+tel);


        //map좌표 가져올 영역 ^-^
        Geocoder geocoder= new Geocoder(this, Locale.KOREA);
        try {
            List<Address> list= geocoder.getFromLocationName(addr, 3);//list에 addr로부터 주소를 얻어옴
            //경도, 위도 가져오기
            lat= list.get(0).getLatitude();
            lng= list.get(0).getLongitude();

            //Toast.makeText(this, lat+","+lng, Toast.LENGTH_SHORT).show();
            mapFragment.getMapAsync(new OnMapReadyCallback() {//비동기 방식으로 맵 가져오기
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    LatLng seoul= new LatLng(lat, lng);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 18));

//                    MarkerOptions markerOptions= new MarkerOptions();
//                    markerOptions.title(item.name);
//                    markerOptions.snippet(item.addrdo);
//                    markerOptions.position(new LatLng(lat, lng));
//                    googleMap.addMarker(markerOptions);

                    //SecondActivity에서 원하는 병원 클릭했을때 그 위치에 마커 적용
                    MarkerOptions options= new MarkerOptions();
                    options.title(name);
                    options.snippet(addr);
                    options.position(seoul);
                    googleMap.addMarker(options);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }


        //Toast.makeText(this, name+"\n"+addr+"\n"+tel, Toast.LENGTH_SHORT).show();




    }
}