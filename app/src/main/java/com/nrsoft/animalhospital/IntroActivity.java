package com.nrsoft.animalhospital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IntroActivity extends AppCompatActivity {

    TextView tv;
    boolean isloaded= false;
    boolean isTimeover= false;
    int loadCount= 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//화면에 아직 안보일때
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);




    }

    @Override
    protected void onResume() { //화면에 보일때
        super.onResume();

        new Handler().postDelayed(new Runnable() { //4초 있다가 화면 전환
            @Override
            public void run() {
                isTimeover=true;
                if(isloaded){
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(IntroActivity.this, "데이터 로딩중입니다", Toast.LENGTH_SHORT).show();
                }
            }
        }, 10000);

        loadData();

        ProgressBar progressBar = findViewById(R.id.pg);
        progressBar.setIndeterminate(false);
        progressBar.setProgress(80);

    }

    void loadData() {

        G.items.clear();

        Thread t= new Thread(){
            @Override
            public void run() {
                super.run();
                StringBuffer buffer= new StringBuffer();
                String ApiKey="546879544d796e3039306a4c4f724f";

                String address= "http://openapi.seoul.go.kr:8088/546879544d796e3039306a4c4f724f/xml/LOCALDATA_020301/1/30/";

                try {
                    URL url= new URL(address);
                    InputStream is= url.openStream();

                    XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
                    XmlPullParser xpp= factory.newPullParser();
                    xpp.setInput(new InputStreamReader(is, StandardCharsets.UTF_8));


                    Item item=null;


                    int eventType= xpp.getEventType();
                    while(eventType != XmlPullParser.END_DOCUMENT){
                        switch(eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;
                            case XmlPullParser.START_TAG:
                                String tag = xpp.getName();
                                Log.i("aa", tag+"");
                                if (tag.equals("row")){
                                    item= new Item();

                                }else if (tag.equals("BPLCNM")) {
                                    xpp.next();
                                    item.name= xpp.getText();

                                } else if (tag.equals("SITEWHLADDR")) {
                                    xpp.next();
                                    item.addrgi= xpp.getText();

                                } else if (tag.equals("RDNWHLADDR")) {
                                    xpp.next();
                                    item.addrdo= xpp.getText();

                                } else if (tag.equals("SITETEL")) {
                                    xpp.next();
                                    item.tel= xpp.getText();

                                } else if (tag.equals("TRDSTATENM")) {
                                    xpp.next();
                                    item.condition= xpp.getText();

                                } else if(tag.equals("MGTNO")){
                                    xpp.next();
                                    item.mgtNo= xpp.getText();
                                }
                                break;
                            case XmlPullParser.TEXT:
                                break;
                            case XmlPullParser.END_TAG:
                                String tag2 = xpp.getName();
                                if(tag2.equals("row")){
                                    G.items.add(item);
                                }
                                break;

                        }
                        eventType= xpp.next();

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.pg).setVisibility(View.GONE);
                        }
                    });

                    isloaded=true;
                    if(isTimeover){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent= new Intent(IntroActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        t.start();

    }
}