package com.example.clothes_matching;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

/**
 * Created by q on 2017-07-15.
 */

public class TypeTypeFragment extends Fragment {
    public TypeTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_type_type, container, false);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.top :
                        MainActivity.tag += "1";
                        break;
                    case R.id.bottom :
                        MainActivity.tag += "2";
                        break;
                    case R.id.dress :
                        MainActivity.tag += "3";
                        break;
                }

            }
        });

        ImageView go_3 = (ImageView) view.findViewById(R.id.go_3);
        go_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToneTypeFragment fragment = new ToneTypeFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        ImageView back_3 = (ImageView) view.findViewById(R.id.back_3);
        back_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenderTypeFragment fragment = new GenderTypeFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}

