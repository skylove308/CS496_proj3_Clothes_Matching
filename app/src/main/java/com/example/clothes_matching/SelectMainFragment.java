package com.example.clothes_matching;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMainFragment extends Fragment {


    public SelectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select_main, container, false);

        TextView textView1 = (TextView)view.findViewById(R.id.sm1);
        textView1.setText(Html.fromHtml(getString(R.string.select_main_1)));

        TextView textView2 = (TextView)view.findViewById(R.id.sm2);
        textView2.setText(Html.fromHtml(getString(R.string.select_main_2)));

        TextView textView3 = (TextView)view.findViewById(R.id.sm6);
        textView3.setText(Html.fromHtml(getString(R.string.select_main_6)));

        TextView textView4 = (TextView)view.findViewById(R.id.sm8);
        textView4.setText(Html.fromHtml(getString(R.string.select_main_8)));



        Animation animation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.alpha);
        view.startAnimation(animation);

        final LottieAnimationView animationView = (LottieAnimationView) view.findViewById(R.id.animation_view);

        animationView.setAnimation("simple_check.json");
        animationView.playAnimation();
        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationView.playAnimation();

                Handler handler = new Handler(){
                    public void handleMessage(Message msg){
                        super.handleMessage(msg);
                        GenderTypeFragment fragment = new GenderTypeFragment();
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        fragmentTransaction.replace(R.id.MainView, fragment);
                        fragmentTransaction.commit();
                    }
                };
                handler.sendEmptyMessageDelayed(0, 1000);
            }
        });

        return view;
    }

}
