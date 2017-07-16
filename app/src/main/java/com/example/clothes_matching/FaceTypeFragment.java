package com.example.clothes_matching;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaceTypeFragment extends Fragment {
    int faces = 1;

    public FaceTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_face_type, container, false);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup5);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.roundfacer :
                        faces = 1;
                        break;
                    case R.id.intrianglefacer :
                        faces = 2;
                        break;
                    case R.id.angularfacer :
                        faces = 3;
                        break;
                    case R.id.trianglefacer :
                        faces = 4;
                        break;
                    case R.id.ellipsefacer :
                        faces = 5;
                        break;
                }

            }
        });

        ImageView go_5 = (ImageView) view.findViewById(R.id.go_5);
        go_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tag += String.valueOf(faces);
                BodyTypeFragment fragment = new BodyTypeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
                System.out.println(MainActivity.tag);
            }
        });

        ImageView back_5 = (ImageView) view.findViewById(R.id.back_5);
        back_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tag = MainActivity.tag.substring(0,MainActivity.tag.length()-1);
                ToneTypeFragment fragment = new ToneTypeFragment();

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
