package edu.drury.mcs.Dnav.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import edu.drury.mcs.Dnav.JavaClass.DnavDBAdapter;

public class SplashScreen extends AppCompatActivity {

    private SQLiteDatabase db;
    private final String SELECT_TIMESTAMPS = "SELECT * FROM " + DnavDBAdapter.TABLE_TIMESTAMPS;

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

                    //surround this with a check for network connectivity
                    //creates sqlite database if it doesn't exist, opens it otherwise
                    initializeSQLite();

                    //checks for updates to the database
                    ArrayList<String> tablesToUpdate = checkForUpdates();

                    if(tablesToUpdate.size() > 0){
                        updateTables(tablesToUpdate);
                    }

                    db.close();

                    //resume non-network functionality

                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        timeThread.start();

    }

    private void initializeSQLite(){
        DnavDBAdapter dbAdapter = new DnavDBAdapter(this);
        db = dbAdapter.getWritableDB();
    }

    private ArrayList<String> checkForUpdates(){

        //Get request should be encoded in the future

        String target = "http://mcs.drury.edu/dnav/checkforupdates.php?";
        Cursor cursor = db.rawQuery(SELECT_TIMESTAMPS, null);
        while(cursor.moveToNext())
        {
            target += cursor.getString(cursor.getColumnIndex(DnavDBAdapter.TNAME))+"="+cursor.getInt(cursor.getColumnIndex(DnavDBAdapter.TCOUNTER));
            if(!cursor.isLast())
            {
                target+="&";
            }
        }
        cursor.close();

        System.out.println("Attempted url = "+target);

        //NOTE: If there is no need to update any tables, the empty response causes a JSON Exception. However, this does not hamper any functionality currently.
        try {
            URL url = new URL(target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the connection
            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            Scanner scanster = new Scanner(in);

            String responseBody = "";

            while(scanster.hasNextLine())
            {
                responseBody+=scanster.nextLine();
            }
            scanster.close();
            in.close();
            conn.disconnect();

            System.out.println("Response Body: " + responseBody);

            JSONObject json = new JSONObject(responseBody);

            System.out.println("JSON: " + json);

            Iterator<String> it = json.keys();

            ArrayList<String> returnValue = new ArrayList<>();

            while(it.hasNext()){
                returnValue.add(it.next());
            }

            return returnValue;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<String>();
    }

    private void updateTables(ArrayList<String> tablesToUpdate){

        //Get request should be encoded in the future

        String target = "http://mcs.drury.edu/dnav/requestupdates.php?";

        target+=DnavDBAdapter.TABLE_TIMESTAMPS+"=0&";

        for(String str : tablesToUpdate)
        {
            target += str + "=0";

            if(tablesToUpdate.indexOf(str) != tablesToUpdate.size()-1){
                target += "&";
            }
        }

        try {
            URL url = new URL(target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the connection
            conn.connect();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            Scanner scanster = new Scanner(in);

            String responseBody = "";

            while(scanster.hasNextLine())
            {
                responseBody+=scanster.nextLine();
            }

            scanster.close();
            in.close();
            conn.disconnect();

            System.out.println("Response Body: " + responseBody);

            JSONObject json = new JSONObject(responseBody);

            System.out.println("JSON: " + json);

            Iterator<String> tableIterator = json.keys();

            while(tableIterator.hasNext()){
                String table = tableIterator.next();

                JSONArray rows = json.getJSONArray(table);

                db.delete(table, null, null);
                for(int i = 0; i < rows.length(); i++){

                    JSONObject tuple = (JSONObject) rows.get(i);
                    Iterator<String> columns = tuple.keys();
                    ContentValues columnValues = new ContentValues();
                    while(columns.hasNext()){
                        String col = columns.next();
                        Object value = tuple.get(col);

                        System.out.println(value);

                        if(value instanceof String)
                            columnValues.put(col, (String) value);
                        else if(value instanceof Double)
                            columnValues.put(col, (Double) value);
                        else if(value instanceof Integer)
                            columnValues.put(col, (Integer) value);
                        else
                            columnValues.putNull(col);
                    }
                    db.insertWithOnConflict(table, null , columnValues, SQLiteDatabase.CONFLICT_REPLACE);
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
