package com.example.clothes_matching;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView title = (TextView)view.findViewById(R.id.Title_Name);
        title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/ENSTEN.TTF"));
        title.setText("NARAE");

        TextView subtitle = (TextView)view.findViewById(R.id.subtitle_name);
        subtitle.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HANCooljazzB.ttf"));
        subtitle.setText("너를 위한 단 하나의 쇼핑몰 나래");

        return view;

    }

}
