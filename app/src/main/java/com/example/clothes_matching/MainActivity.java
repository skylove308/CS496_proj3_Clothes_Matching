package com.example.clothes_matching;


import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.LoginStatusCallback;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static String tag = "";
    private AccessTokenTracker accessTokenTracker;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tag = "";
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView home = (ImageView) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment fragment = new MainFragment();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.MainView, fragment);
                fragmentTransaction.commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View header = navigationView.getHeaderView(0);

        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) header.findViewById(R.id.login_button);
        final TextView navtext1 = (TextView)header.findViewById(R.id.textView1);
        final TextView navtext2 = (TextView)header.findViewById(R.id.textView);

        if(AccessToken.getCurrentAccessToken() != null)
        {
            GetTask getTask = new GetTask();
            String userdata = "";
            try
            {
                userdata = getTask.execute("http://13.124.144.112:8090/api/person/" + AccessToken.getCurrentAccessToken().getUserId()).get();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(userdata);
            JSONArray userarray = null;
            JSONObject userjson = null;
            try
            {
                userarray = new JSONArray(userdata);
                userjson = userarray.getJSONObject(0);
                navtext1.setText(userjson.getString("name"));
                navtext2.setText(userjson.getString("email"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            header.invalidate();
        }
        else
        {
            navtext1.setText("환영합니다");
            navtext2.setText("android.studio@android.com");
            header.invalidate();
        }

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        String url = "http://13.124.144.112:8090/api/person";
                        String res = "";
                        String userID = "";
                        String jsonStr = "";

                        try {
                            userID = object.getString("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final GetTask getTask = new GetTask();
                        try {
                            res = getTask.execute(url+"/"+userID).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        if(res.equals("[]")){
                            String imageUrl = "https://graph.facebook.com/"+userID+"/picture";

                            try {
                                jsonStr += "{\"fbid\" : " + "\"" + userID + "\"," +
                                        "\"name\" : " + "\"" + object.getString("name") + "\"," +
                                        "\"image\" : " + "\"" + imageUrl + "\"," +
                                        "\"gender\" : " + "\"" + object.getString("gender") + "\"," +
                                        "\"email\" : " + "\"" + object.getString("email") + "\"," +
                                        "\"mytypelist\" : " + "[]}";
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try
                            {
                                navtext1.setText(object.getString("name"));
                                navtext2.setText(object.getString("email"));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            final PostTask postTask = new PostTask(url, jsonStr);
                            postTask.execute();
                        }
                        else
                        {
                            JSONArray userarray = null;
                            JSONObject userjson = null;
                            try
                            {
                                userarray = new JSONArray(res);
                                userjson = userarray.getJSONObject(0);
                                navtext1.setText(userjson.getString("name"));
                                navtext2.setText(userjson.getString("email"));
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null)
                {
                    navtext1.setText("환영합니다");
                    navtext2.setText("android.studio@android.com");
                    header.invalidate();
                }
            }
        };

        MainFragment fragment = new MainFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.MainView, fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_choose) {
            if(AccessToken.getCurrentAccessToken() != null)
            {
                fragment = new SelectMainFragment();
            }
        }
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.MainView, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
