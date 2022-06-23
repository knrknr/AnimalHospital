package com.nrsoft.animalhospital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    //서울시 인증키 : 546879544d796e3039306a4c4f724f

    BottomNavigationView bnv;
    Fragment[] fragments= new Fragment[3];
    FragmentManager fragmentManager;

    boolean isloaded= false;
    boolean isTimeover= false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager= getSupportFragmentManager();

        fragments[0]= new MapFragment();
        fragmentManager.beginTransaction().add(R.id.container, fragments[0]).hide(fragments[0]).commit();

        fragments[1]= new HomeFragment();
        fragmentManager.beginTransaction().add(R.id.container, fragments[1]).commit();

        bnv= findViewById(R.id.bnv);
        bnv.setSelectedItemId(R.id.menu_item_home);


        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(fragments[0]!=null) fragmentManager.beginTransaction().hide(fragments[0]).commit();
                if(fragments[1]!=null) fragmentManager.beginTransaction().hide(fragments[1]).commit();
                if(fragments[2]!=null) fragmentManager.beginTransaction().hide(fragments[2]).commit();

                switch (item.getItemId()){
                    case R.id.menu_item_map:
                        fragmentManager.beginTransaction().show(fragments[0]).commit();
                        break;
                    case R.id.menu_item_home:
                        if(fragments[1]==null){
                            fragments[1]=new HomeFragment();
                            fragmentManager.beginTransaction().add(R.id.container, fragments[1]).commit();
                        }
                        fragmentManager.beginTransaction().show(fragments[1]).commit();
                        break;
                    case R.id.menu_item_list:
                        if(fragments[2]==null){
                            fragments[2]=new ListFragment();
                            fragmentManager.beginTransaction().add(R.id.container, fragments[2]).commit();
                        }
                        fragmentManager.beginTransaction().show(fragments[2]).commit();
                        break;
                }
                return true;
            }
        });

        //loadData();


        //데이터베이스 만들기
        SQLiteDatabase db= openOrCreateDatabase("animalhospital", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS favor(num INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, mgtNo TEXT)");


    }

//    void loadData() {
//
//        G.items.clear();
//
//        Thread t= new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                StringBuffer buffer= new StringBuffer();
//                String ApiKey="546879544d796e3039306a4c4f724f";
//
//                String address= "http://openapi.seoul.go.kr:8088/546879544d796e3039306a4c4f724f/xml/LOCALDATA_020301/1/100/";
//
//                try {
//                    URL url= new URL(address);
//                    InputStream is= url.openStream();
//
//                    XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
//                    XmlPullParser xpp= factory.newPullParser();
//                    xpp.setInput(new InputStreamReader(is, StandardCharsets.UTF_8));
//
//
//                    Item item=null;
//
//
//                    int eventType= xpp.getEventType();
//                    while(eventType != XmlPullParser.END_DOCUMENT){
//                        switch(eventType) {
//                            case XmlPullParser.START_DOCUMENT:
//                                break;
//                            case XmlPullParser.START_TAG:
//                                String tag = xpp.getName();
//                                Log.i("aa", tag+"");
//                                if (tag.equals("row")){
//                                    item= new Item();
//
//                                }else if (tag.equals("BPLCNM")) {
//                                    xpp.next();
//                                    item.name= xpp.getText();
//
//                                } else if (tag.equals("SITEWHLADDR")) {
//                                    xpp.next();
//                                    item.addrgi= xpp.getText();
//
//                                } else if (tag.equals("RDNWHLADDR")) {
//                                    xpp.next();
//                                    item.addrdo= xpp.getText();
//
//                                } else if (tag.equals("SITETEL")) {
//                                    xpp.next();
//                                    item.tel= xpp.getText();
//
//                                } else if (tag.equals("TRDSTATENM")) {
//                                    xpp.next();
//                                    item.condition= xpp.getText();
//
//                                } else if(tag.equals("MGTNO")){
//                                    xpp.next();
//                                    item.mgtNo= xpp.getText();
//                                }
//                                break;
//                            case XmlPullParser.TEXT:
//                                break;
//                            case XmlPullParser.END_TAG:
//                                String tag2 = xpp.getName();
//                                if(tag2.equals("row")){
//                                    G.items.add(item);
//                                }
//                                break;
//
//                        }
//                        eventType= xpp.next();
//
//                    }
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            findViewById(R.id.pg).setVisibility(View.GONE);
//                        }
//                    });
//
//                    isloaded=true;
//                    if(isTimeover){
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Intent intent= new Intent(MainActivity.this, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//
//                            }
//                        });
//                    }
//
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        };
//        t.start();
//
//    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}