package edu.drury.mcs.Dnav.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import edu.drury.mcs.Dnav.JavaClass.DnavDBAdapter;

public class SplashScreen extends AppCompatActivity {

    private SQLiteDatabase db;
    private final String SELECT_TIMESTAMPS = "SELECT * FROM " + DnavDBAdapter.TABLE_TIMESTAMPS;

    private AlertDialog needInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Thread timeThread = new Thread(){
            public void run(){

                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {

                    while(needInternet != null && needInternet.isShowing()){
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        doInBack updateDBTask = new doInBack(this);

        try {
            if(!updateDBTask.execute().get()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Warning!");
                builder.setMessage("For the best experience and the most up to date information, please start Dnav with an internet connection.");
                needInternet = builder.create();
                needInternet.show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        timeThread.start();

    }

}
