package com.example.clothes_matching;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
class Items
{
    Items(String aimage, String atext1, String atext2)
    {
        image = aimage;
        text1 = atext1;
        text2 = atext2;
    }
    String image;
    String text1;
    String text2;
}

class ItemsAdapter extends BaseAdapter
{
    ArrayList<Items> arSrc;
    int layout;
    Context maincon;
    LayoutInflater Inflater;

    public ItemsAdapter(Context context, int alayout, ArrayList<Items> aarSrc)
    {
        maincon = context;
        Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
        layout = alayout;
    }

    public int getCount()
    {
        return arSrc.size();
    }
    public String getItem(int position)
    {
        return arSrc.get(position).text1;
    }
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final int pos = position;
        if(convertView == null)
        {
            convertView = Inflater.inflate(layout, parent, false);
        }

        ImageView img =(ImageView)convertView.findViewById(R.id.itemimage);
        byte[] buf = Base64.decode(arSrc.get(pos).image, Base64.DEFAULT);
        Bitmap bitmap1 = BitmapFactory.decodeByteArray(buf, 0, buf.length);
        img.setImageBitmap(bitmap1);

        TextView txt1 = (TextView)convertView.findViewById(R.id.itemtext1);
        txt1.setText(arSrc.get(pos).text1);
        TextView txt2 = (TextView)convertView.findViewById(R.id.itemtext2);
        txt2.setText(arSrc.get(pos).text2);

        return convertView;
    }
}

public class ResultFragment extends Fragment {


    public ResultFragment() {
        // Required empty public constructor
    }
    ListView mResult;
    ItemsAdapter Adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        mResult = (ListView)view.findViewById(R.id.resultlist);
        GetTask getTask = new GetTask();
        ArrayList<Items> arlist = new ArrayList<Items>();
        String gender = String.valueOf(MainActivity.tag.charAt(0));
        String type = String.valueOf(MainActivity.tag.charAt(1));
        String tone = String.valueOf(MainActivity.tag.charAt(2));
        String face = String.valueOf(MainActivity.tag.charAt(3));
        String body = String.valueOf(MainActivity.tag.charAt(4));
        JSONArray hey = null;
        try
        {
            hey = new JSONArray(getTask.execute("http://13.124.144.112:8090/api/closet/clothes/" + gender + "/" + type + "/" + tone + "/" + face + "/" + body).get());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        arlist.clear();
        Random random = new Random();
        if(hey.length()<=5)
        {
            for(int i=0;i<hey.length();i++)
            {
                try
                {
                    JSONObject content = hey.getJSONObject(i);
                    Items newitem = new Items(content.getString("image"), content.getString("name"), content.getString("price") + "원");
                    arlist.add(newitem);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            for(int i=0;i<5;i++)
            {
                try
                {
                    JSONObject content = hey.getJSONObject(random.nextInt(hey.length()));
                    Items newitem = new Items(content.getString("image"), content.getString("name"), content.getString("price") + "원");
                    arlist.add(newitem);
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(AccessToken.getCurrentAccessToken() != null)
        {
            PutTask putTask = new PutTask("http://13.124.144.112:8090/api/person/" + AccessToken.getCurrentAccessToken().getUserId(), "{\"mytype\" : \"" + MainActivity.tag + "\"}");
            try
            {
                putTask.execute("i").get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        Adapter = new ItemsAdapter(getActivity(), R.layout.fragment_result_item, arlist);
        mResult.setAdapter(Adapter);
        MainActivity.tag = "";

        final LottieAnimationView animationView = (LottieAnimationView) view.findViewById(R.id.animation_view3);
        final LottieAnimationView animationView2 = (LottieAnimationView) view.findViewById(R.id.animation_view4);

        animationView.setAnimation("5_stars.json");
        animationView2.setAnimation("5_stars.json");
        animationView.playAnimation();
        animationView.loop(true);
        animationView2.playAnimation();
        animationView.loop(true);

        return view;
    }

}
