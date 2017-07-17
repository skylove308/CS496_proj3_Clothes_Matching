package com.example.clothes_matching;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;



public class ToneTypeFragment extends Fragment {
    int tones = 1;

    public ToneTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tone_type, container, false);

        TextView textView = (TextView)view.findViewById(R.id.personal_color);
        textView.setText("※클릭: 본인의 퍼스널 컬러를 모르신다면?");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalColorFragment fragment = new PersonalColorFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup4);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.spring :
                        tones = 1;
                        break;
                    case R.id.summer :
                        tones = 2;
                        break;
                    case R.id.fall :
                        tones = 3;
                        break;
                    case R.id.winter :
                        tones = 4;
                        break;
                }

            }
        });

        ImageView go_4 = (ImageView) view.findViewById(R.id.go_4);
        go_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tag += String.valueOf(tones);

                FaceTypeFragment fragment = new FaceTypeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
                System.out.println(MainActivity.tag);
            }
        });

        ImageView back_4 = (ImageView) view.findViewById(R.id.back_4);
        back_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tag = MainActivity.tag.substring(0,MainActivity.tag.length()-1);
                TypeTypeFragment fragment = new TypeTypeFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
                System.out.println(MainActivity.tag);
            }
        });

        return view;
    }
}
