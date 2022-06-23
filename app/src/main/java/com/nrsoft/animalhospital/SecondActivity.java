package com.nrsoft.animalhospital;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ArrayList<Item> filterHospitals= new ArrayList<>();
    RecyclerView recyclerView;
    AnimalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        recyclerView= findViewById(R.id.recycler);

        Intent intent= getIntent();
        String s= intent.getStringExtra("name");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Log.i("aaa", G.items.get(0).name);
        for(int i=0; i<G.items.size(); i++){
            Item t= G.items.get(i);
            if(t.name.contains(s)&&t.condition.equals("영업/정상")){
                filterHospitals.add(t);
            }
//            Log.e("aaa",filterHospitals.size()+"");
            Log.e("aaa",G.items.size()+"");
        }
        adapter= new AnimalAdapter(this, filterHospitals);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}