package com.example.clothes_matching;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
class Listitem{
    Listitem(String agender, String atype, String atone, String aface, String abody)
    {
        gender = agender;
        type = atype;
        tone = atone;
        face = aface;
        body = abody;
    }
    String gender;
    String type;
    String tone;
    String face;
    String body;
}

class ListmanAdapter extends BaseAdapter{
    ArrayList<Listitem> arSrc;
    int layout;
    Context maincon;
    LayoutInflater Inflater;

    public ListmanAdapter(Context context, int alayout, ArrayList<Listitem> aarSrc)
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
        return arSrc.get(position).gender;
    }
    public long getItemId(int position)
    {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }

        TextView gender = (TextView)convertView.findViewById(R.id.genderitem);
        TextView type = (TextView)convertView.findViewById(R.id.typeitem);
        TextView tone = (TextView)convertView.findViewById(R.id.toneitem);
        TextView face = (TextView)convertView.findViewById(R.id.faceitem);
        TextView body = (TextView)convertView.findViewById(R.id.bodyitem);

        String[] genders = {"무", "남", "여"};
        String[] types = {"무", "상의", "하의", "원피스/정장"};
        String[] tones = {"무", "봄", "여름", "가을", "겨울"};
        String[] faces = {"무", "둥근형", "역삼각형", "각진형", "삼각형", "타원형"};
        String[] bodies = {"무", "둥근형", "역삼각형", "직사각형", "삼각형", "모래시계형"};

        gender.setText(genders[Integer.parseInt(arSrc.get(pos).gender)]);
        type.setText(types[Integer.parseInt(arSrc.get(pos).type)]);
        tone.setText(tones[Integer.parseInt(arSrc.get(pos).tone)]);
        face.setText(faces[Integer.parseInt(arSrc.get(pos).face)]);
        body.setText(bodies[Integer.parseInt(arSrc.get(pos).body)]);

        return convertView;
    }
}

public class SearchListFragment extends Fragment {


    public SearchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        GetTask getTask = new GetTask();
        final PutTask putTask = new PutTask("http://13.124.144.112:8090/api/person/clear/" + AccessToken.getCurrentAccessToken().getUserId(), "{}");
        JSONArray hey = null;
        JSONObject what = null;
        Button clearbtn = (Button)view.findViewById(R.id.clearlist);
        final ListView searchlistview = (ListView)view.findViewById(R.id.searchlist);
        ListmanAdapter sAdapter;
        try
        {
            hey = new JSONArray(getTask.execute("http://13.124.144.112:8090/api/person/" + AccessToken.getCurrentAccessToken().getUserId()).get());
            what = hey.getJSONObject(0);
            if(what.getString("mytypelist").toString().equals("[]"))
            {

            }
            else
            {
                String mytypelist = what.getString("mytypelist");
                final ArrayList<Listitem> listmoth = new ArrayList<Listitem>();
                String reallist = mytypelist.substring(1,mytypelist.length()-1);
                final String[] reallists = reallist.split(",");
                for(int i=0;i<reallists.length;i++)
                {
                    reallists[i] = reallists[i].substring(1,reallists[i].length()-1);
                    Listitem newlistitem = new Listitem(String.valueOf(reallists[i].charAt(0)), String.valueOf(reallists[i].charAt(1)), String.valueOf(reallists[i].charAt(2)), String.valueOf(reallists[i].charAt(3)), String.valueOf(reallists[i].charAt(4)));
                    listmoth.add(newlistitem);
                }
                sAdapter = new ListmanAdapter(getActivity(), R.layout.fragment_search_list_item, listmoth);
                searchlistview.setAdapter(sAdapter);
                searchlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MainActivity.tag = "" + listmoth.get(position).gender + listmoth.get(position).type + listmoth.get(position).tone + listmoth.get(position).face + listmoth.get(position).body;
                        PutTask putTask1 = new PutTask("http://13.124.144.112:8090/api/person/old/" + AccessToken.getCurrentAccessToken().getUserId(), "{\"oldtype\" : " + "\"" + MainActivity.tag + "\"}");
                        try
                        {
                            putTask1.execute("i").get();
                        }
                        catch (ExecutionException e)
                        {
                            e.printStackTrace();
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }

                        ResultFragment fragment = new ResultFragment();

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        fragmentTransaction.replace(R.id.MainView, fragment);
                        fragmentTransaction.commit();
                    }
                });
            }
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        final boolean[] clickflag = new boolean[1];
        clickflag[0] = true;

        clearbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickflag[0] == true)
                {
                    try
                    {
                        clickflag[0] = false;
                        putTask.execute("i").get();
                        ArrayList<Listitem> listmoth = new ArrayList<Listitem>();
                        ListmanAdapter stAdapter = new ListmanAdapter(getActivity(), R.layout.fragment_search_list_item, listmoth);
                        searchlistview.setAdapter(stAdapter);
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
            }
        });
        return view;
    }

}
