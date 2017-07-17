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
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalColorFragment extends Fragment {


    public PersonalColorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_color, container, false);

        TextView textView1 = (TextView)view.findViewById(R.id.pc1);
        textView1.setText(Html.fromHtml(getString(R.string.personal_color_1)));

        TextView textView2 = (TextView)view.findViewById(R.id.pc2);
        textView2.setText(Html.fromHtml(getString(R.string.personal_color_2)));

        TextView textView3 = (TextView)view.findViewById(R.id.pc4);
        textView3.setText(Html.fromHtml(getString(R.string.personal_color_3)));

        ImageView back_personal = (ImageView) view.findViewById(R.id.back_personal);
        back_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToneTypeFragment fragment = new ToneTypeFragment();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}
