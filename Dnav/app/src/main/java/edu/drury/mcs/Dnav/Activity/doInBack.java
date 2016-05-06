package edu.drury.mcs.Dnav.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

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

/**
 * Created by yma004 on 5/4/2016.
 */
public class doInBack extends AsyncTask<Void, Void, Boolean> {

    private SQLiteDatabase db;
    private final String SELECT_TIMESTAMPS = "SELECT * FROM " + DnavDBAdapter.TABLE_TIMESTAMPS;
    private AlertDialog needInternet;

    private Context context;


    public doInBack(Context con){
        this.context = con;
    }

    @Override
    protected Boolean doInBackground(Void... params) {


        //surround this with a check for network connectivity
        //creates sqlite database if it doesn't exist, opens it otherwise
        initializeSQLite();

        ConnectivityManager conMan = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));

        NetworkInfo netFo = conMan.getActiveNetworkInfo();

        if (netFo != null && netFo.isConnected()) {

            //checks for updates to the database
            ArrayList<String> tablesToUpdate = checkForUpdates();

            if (tablesToUpdate.size() > 0) {
                updateTables(tablesToUpdate);
            }
        } else {



            db.close();
            return false;

        }

        db.close();

        return true;


    }

    private void initializeSQLite() {
        DnavDBAdapter dbAdapter = new DnavDBAdapter(context);
        db = dbAdapter.getWritableDB();
    }

    private ArrayList<String> checkForUpdates() {

        //Get request should be encoded in the future

        String target = "http://mcs.drury.edu/dnav/checkforupdates.php?";
        Cursor cursor = db.rawQuery(SELECT_TIMESTAMPS, null);
        while (cursor.moveToNext()) {
            target += cursor.getString(cursor.getColumnIndex(DnavDBAdapter.TNAME)) + "=" + cursor.getInt(cursor.getColumnIndex(DnavDBAdapter.TCOUNTER));
            if (!cursor.isLast()) {
                target += "&";
            }
        }
        cursor.close();

        System.out.println("Attempted url = " + target);

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

            while (scanster.hasNextLine()) {
                responseBody += scanster.nextLine();
            }
            scanster.close();
            in.close();
            conn.disconnect();

            System.out.println("Response Body: " + responseBody);

            JSONObject json = new JSONObject(responseBody);

            System.out.println("JSON: " + json);

            Iterator<String> it = json.keys();

            ArrayList<String> returnValue = new ArrayList<>();

            while (it.hasNext()) {
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

    private void updateTables(ArrayList<String> tablesToUpdate) {

        //Get request should be encoded in the future

        String target = "http://mcs.drury.edu/dnav/requestupdates.php?";

        target += DnavDBAdapter.TABLE_TIMESTAMPS + "=0&";

        for (String str : tablesToUpdate) {
            target += str + "=0";

            if (tablesToUpdate.indexOf(str) != tablesToUpdate.size() - 1) {
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

            while (scanster.hasNextLine()) {
                responseBody += scanster.nextLine();
            }

            scanster.close();
            in.close();
            conn.disconnect();

            System.out.println("Response Body: " + responseBody);

            JSONObject json = new JSONObject(responseBody);

            System.out.println("JSON: " + json);

            Iterator<String> tableIterator = json.keys();

            while (tableIterator.hasNext()) {
                String table = tableIterator.next();

                JSONArray rows = json.getJSONArray(table);

                db.delete(table, null, null);
                for (int i = 0; i < rows.length(); i++) {

                    JSONObject tuple = (JSONObject) rows.get(i);
                    Iterator<String> columns = tuple.keys();
                    ContentValues columnValues = new ContentValues();
                    while (columns.hasNext()) {
                        String col = columns.next();
                        Object value = tuple.get(col);

                        System.out.println(value);

                        if (value instanceof String)
                            columnValues.put(col, (String) value);
                        else if (value instanceof Double)
                            columnValues.put(col, (Double) value);
                        else if (value instanceof Integer)
                            columnValues.put(col, (Integer) value);
                        else
                            columnValues.putNull(col);
                    }
                    db.insertWithOnConflict(table, null, columnValues, SQLiteDatabase.CONFLICT_REPLACE);
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
