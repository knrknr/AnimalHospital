package com.nrsoft.animalhospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {


    EditText et;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);



        return view;




    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        et= view.findViewById(R.id.et_home);
        view.findViewById(R.id.ib_search).setOnClickListener(v->{
            String s= et.getText().toString();
            Intent intent= new Intent(getActivity(), SecondActivity.class);
            intent.putExtra("name",s);
            startActivity(intent);
        });




    }
}
