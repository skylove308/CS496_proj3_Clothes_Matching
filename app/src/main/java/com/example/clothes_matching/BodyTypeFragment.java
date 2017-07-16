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
public class BodyTypeFragment extends Fragment {

    int bodies = 1;

    public BodyTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_body_type, container, false);
        RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup6);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.round_body1 :
                        bodies = 1;
                        break;
                    case R.id.intriangle_body1 :
                        bodies = 2;
                        break;
                    case R.id.rectangular_body1 :
                        bodies = 3;
                        break;
                    case R.id.triangle_body1 :
                        bodies = 4;
                        break;
                    case R.id.sandwatch_body1 :
                        bodies = 5;
                        break;
                }

            }
        });

        ImageView finish = (ImageView) view.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.tag += String.valueOf(bodies);

                BodyTypeFragment fragment = new BodyTypeFragment();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
                System.out.println(MainActivity.tag);
            }
        });

        ImageView back_6 = (ImageView) view.findViewById(R.id.back_6);
        back_6.setOnClickListener(new View.OnClickListener() {
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
