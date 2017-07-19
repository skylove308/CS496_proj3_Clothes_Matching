package com.example.q.dbsaving;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by q on 2017-07-08.
 */

class PostTask extends AsyncTask<String, String , String> {
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private JSONObject json;
    private String url;
    private OkHttpClient client;

    //given: url, person object
    //do: make client, save url, save information of person in json.

    PostTask(String url, String jsonInfo) {
        client = new OkHttpClient();
        this.url = url;
        makeJson(jsonInfo);
    }

    PostTask(String url, JSONObject json) {
        client = new OkHttpClient();
        this.url = url;
        this.json = json;
    }

    @Override
    protected String doInBackground(String... params){
        //make request body
        RequestBody body = RequestBody.create(JSON, json.toString());

        //make request with url, request body
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            //get response of the request
            Response response = client.newCall(request).execute();
            String hey =  response.body().string();
            response.body().close();
            return hey;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void makeJson (String jsonInfo){
        try {
            this.json = new JSONObject(jsonInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

