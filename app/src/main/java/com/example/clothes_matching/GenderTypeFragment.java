package com.example.clothes_matching;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenderTypeFragment extends Fragment {


    public GenderTypeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gender_type, container, false);
        final RadioGroup radioGroup = (RadioGroup)view.findViewById(R.id.radioGroup);

        ImageView go_2 = (ImageView) view.findViewById(R.id.go_2);

        Log.d("나와","*************************************");
        go_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("선택", "**************************************");
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.male :
                                Log.d("남자선택", "**************************************");
                                break;
                            case R.id.female :
                                break;
                        }

                    }
                });
                TypeTypeFragment fragment = new TypeTypeFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        ImageView back_2 = (ImageView) view.findViewById(R.id.back_2);
        back_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectMainFragment fragment = new SelectMainFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
