package com.example.q.dbsaving;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int[] images = {R.drawable.img1, R.drawable.img2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listupload();
    }

    void listupload()
    {
        String listStr = null;
        String nameStr = null;
        String priceStr = null;
        try
        {
            InputStream stream = getAssets().open("lists.txt");
            byte[] buf = new byte[stream.available()];
            stream.read(buf);
            stream.close();
            listStr = new String(buf, "UTF-8");

            InputStream stream1 = getAssets().open("names.txt");
            byte[] buf1 = new byte[stream1.available()];
            stream1.read(buf1);
            stream1.close();
            nameStr = new String(buf1, "UTF-8");

            InputStream stream2 = getAssets().open("prices.txt");
            byte[] buf2 = new byte[stream2.available()];
            stream2.read(buf2);
            stream2.close();
            priceStr = new String(buf2, "UTF-8");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        String[] keng = listStr.split("\n");
        String[] names = nameStr.split("\n");
        String[] prices = priceStr.split("\n");

        for(int i=0; i<keng.length;i++)
        {
            String[] result = asdf(keng[i]);
            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), images[i]);
            //mBitmap = Bitmap.createScaledBitmap(mBitmap, 100, 100, true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] buf = stream.toByteArray();
            String encodedImage = Base64.encodeToString(buf, Base64.DEFAULT);

            String jsontype = "{\"name\" : " + "\"" + names[i] + "\"," +
                                "\"image\" : " + "\"" + encodedImage + "\"," +
                                "\"gender\" : "  + result[0]  + "," +
                                "\"type\" : " + result[1] + "," +
                                "\"tone\" : " + result[2] + "," +
                                "\"face\" : " + result[3] + "," +
                                "\"bodies\" : " +  result[4] + "," +
                                "\"price\" : " + "\"" + prices[i] + "\"}";
            PostTask postTask = new PostTask("http://13.124.144.112:8090/api/closet", jsontype);
            postTask.execute("i");
        }
    }


    String[] asdf(String kkl)
    {
        ArrayList<String[]> hey = new ArrayList<String[]>();

        for(int i=0;i<kkl.length();i++)
        {
            if(("" + kkl.charAt(i)).equals("("))
            {
                String[] what = new String[5];
                i++;
                int j=0;
                while(!(("" + kkl.charAt(i)).equals(")")))
                {
                    what[j] = "" + kkl.charAt(i);
                    j++;
                    i++;
                }
                hey.add(what);
            }
            else
            {
                String[] crk = new String[1];
                crk[0] = "" + kkl.charAt(i);
                hey.add(crk);
            }
        }

        String gender = "[";
        String type = "[";
        String tone = "[";
        String face = "[";
        String bodies = "[";

        for(int i=0;i<hey.get(0).length;i++)
        {
            if((hey.get(0))[i] != null)
            {
                gender += "\"" + (hey.get(0))[i] + "\"" + ",";
            }
        }
        gender = gender.substring(0,gender.length()-1);
        gender += "]";

        for(int i=0;i<hey.get(1).length;i++)
        {
            if((hey.get(1))[i] != null)
            {
                type += "\"" + (hey.get(1))[i] + "\"" + ",";
            }
        }
        type = type.substring(0,type.length()-1);
        type += "]";

        for(int i=0;i<hey.get(2).length;i++)
        {
            if((hey.get(2))[i] != null)
            {
                tone += "\"" + (hey.get(2))[i] + "\"" + ",";
            }
        }
        tone = tone.substring(0,tone.length()-1);
        tone += "]";

        for(int i=0;i<hey.get(3).length;i++)
        {
            if((hey.get(3))[i] != null)
            {
                face += "\"" + (hey.get(3))[i] + "\"" + ",";
            }
        }
        face = face.substring(0,face.length()-1);
        face += "]";

        for(int i=0;i<hey.get(4).length;i++)
        {
            if((hey.get(4))[i] != null)
            {
                bodies += "\"" + (hey.get(4))[i] + "\"" + ",";
            }
        }
        bodies = bodies.substring(0,bodies.length()-1);
        bodies += "]";

        String[] hey1 = new String[5];
        hey1[0] = gender;
        hey1[1] = type;
        hey1[2] = tone;
        hey1[3] = face;
        hey1[4] = bodies;
        return hey1;
    }
}
