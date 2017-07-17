package com.example.clothes_matching;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


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

        TextView textView1 = (TextView)view.findViewById(R.id.body_text);
        textView1.setText(Html.fromHtml(getString(R.string.body)));

        final RadioButton m_one = (RadioButton) view.findViewById(R.id.round_body1);
        final RadioButton m_two = (RadioButton) view.findViewById(R.id.intriangle_body1);
        final RadioButton m_three = (RadioButton) view.findViewById(R.id.rectangular_body1);
        final RadioButton m_four = (RadioButton) view.findViewById(R.id.triangle_body1);
        final RadioButton m_five = (RadioButton) view.findViewById(R.id.sandwatch_body1);


        m_one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(true);
                m_two.setChecked(false);
                m_three.setChecked(false);
                m_four.setChecked(false);
                m_five.setChecked(false);
                bodies = 1;
            }
        });

        m_two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(true);
                m_three.setChecked(false);
                m_four.setChecked(false);
                m_five.setChecked(false);
                bodies = 2;
            }
        });

        m_three.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(false);
                m_three.setChecked(true);
                m_four.setChecked(false);
                m_five.setChecked(false);
                bodies = 3;
            }
        });
        m_four.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(false);
                m_three.setChecked(false);
                m_four.setChecked(true);
                m_five.setChecked(false);
                bodies = 4;
            }
        });
        m_five.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                m_one.setChecked(false);
                m_two.setChecked(false);
                m_three.setChecked(false);
                m_four.setChecked(false);
                m_five.setChecked(true);
                bodies = 5;
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
