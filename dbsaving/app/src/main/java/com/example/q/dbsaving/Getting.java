package com.example.q.dbsaving;

import java.util.concurrent.ExecutionException;

/**
 * Created by q on 2017-07-14.
 */

public class Getting {
    static void getting(int gender, int type, int tone, int face, int bodies)
    {
        String geturl = "http://13.124.144.112:8090/api/closet/clothes/";
        GetTask getTask = new GetTask();
        String aka = "";
        try
        {
            aka = getTask.execute(geturl + String.valueOf(gender) + "/" + String.valueOf(type) + "/" + String.valueOf(tone) + "/" + String.valueOf(face) + "/" + String.valueOf(bodies)).get();
            System.out.println(aka);
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

    public static void main(String[] args)
    {
        getting(2,1,3,3,2);
    }
}
