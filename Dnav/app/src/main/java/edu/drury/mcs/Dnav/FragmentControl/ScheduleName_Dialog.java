package edu.drury.mcs.Dnav.FragmentControl;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.Marker;

import java.util.Random;

import edu.drury.mcs.Dnav.Activity.GenerateCourse;
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleName_Dialog extends DialogFragment  {

    public final static String EXTRA_CURRENTSCHE = "edu.drury.mcs.Dnav.CURRENTSCHE" ;
    public final static String EXTRA_TITLE = "edu.drury.mcs.Dnav.TITLE";
    Button cancel, create;
    EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog_view = inflater.inflate(R.layout.schedulename_dialog, null);
        //Infflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(dialog_view);

        editText = (EditText) dialog_view.findViewById(R.id.text_new);
        cancel = (Button) dialog_view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleName_Dialog.this.getDialog().cancel();
            }
        });

        create = (Button) dialog_view.findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String scheduleName = editText.getText().toString();
                Random randy = new Random();
                //need consider Schedule ID also
                Schedule sched = new Schedule("S"+Long.toString(System.currentTimeMillis()/1000)+"-"+Integer.toString(randy.nextInt(1000)),scheduleName);
                XMLController xcont= new XMLController(getActivity());
                xcont.addSchedule(sched);


                //set up intent to pass to generateSchedule Activity
                Intent intent = new Intent(getActivity(), GenerateCourse.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(EXTRA_CURRENTSCHE, sched);
                intent.putExtras(mBundle);

                ScheduleName_Dialog.this.getDialog().cancel();
                startActivity(intent);



            }
        });

        return builder.create();
    }



}
