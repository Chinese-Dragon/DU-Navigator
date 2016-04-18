package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mark93 on 4/17/2016.
 */
public class Message {
    public static void message(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
