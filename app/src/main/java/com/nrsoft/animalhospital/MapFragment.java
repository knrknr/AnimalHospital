package com.nrsoft.animalhospital;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {
    //구글 맵 인증키 다운 - https://developers.google.com/maps/documentation/android-sdk/get-api-key에서 사용자 정보 페이지로 이동
    //구글 지도 api 키 - AIzaSyAevzyGffqYpU9iHw1lEt3SuMAAHCWrYd4




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        return view;

    };

    @Override
    public void onResume() {
        super.onResume();
        showMap();

    }
    SupportMapFragment supportMapFragment;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //tv= view.findViewById(R.id.tv);

        FragmentManager fragmentManager= getChildFragmentManager();
        supportMapFragment= (SupportMapFragment) fragmentManager.findFragmentById(R.id.frag_map);
//        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
//
//
//            @Override
//            public void onMapReady(@NonNull GoogleMap googleMap) {
//
//
//                LatLng seoul= new LatLng( 37.5609, 127.0347);
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,16));
//
//                MarkerOptions markerOptions= new MarkerOptions();
//                markerOptions.title("미래IT캠퍼스");
//                markerOptions.position(seoul);
//
//                //마커 클릭시 보여지는 툴팁박스 만들기
//                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                    @Override
//                    public void onInfoWindowClick(@NonNull Marker marker) {
//                        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse());
//                        startActivity(intent);
//
//                    }
//                });
//
//            }
//        });

    }
    void showMap(){
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                LatLng seoul= new LatLng(37.566580, 126.977938);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul, 12));

                googleMap.getUiSettings().isZoomControlsEnabled();
                Geocoder geocoder= new Geocoder(getActivity(), Locale.KOREA);

                for(Item item: G.items){
                    try {
                        List<Address> addresses = geocoder.getFromLocationName(item.addrdo+"", 3);
                        if(addresses.size()!=0){
                            Address address= addresses.get(0);
                            double lat= address.getLatitude();
                            double lng= address.getLongitude();

                            MarkerOptions markerOptions= new MarkerOptions();
                            markerOptions.title(item.name);
                            markerOptions.snippet(item.addrdo);
                            markerOptions.position(new LatLng(lat, lng));
                            googleMap.addMarker(markerOptions);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }





//                MarkerOptions markerOptions= new MarkerOptions();
//                markerOptions.title(items.get(0).name);
//                double lat= Double.parseDouble(items.get(0).latitude);
//                double lng= Double.parseDouble(items.get(0).longitude);
//                LatLng pos= new LatLng(lat,lng);
//                markerOptions.position(pos);
//                googleMap.addMarker(markerOptions);
            }
        });

    }


}
