package edu.drury.mcs.Dnav.JavaClass;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import edu.drury.mcs.Dnav.R;

/**
 * Created by mark93 on 4/24/2016.
 */
public class CourseDetailDialog extends DialogFragment {
    private Course current_course;
    private LayoutInflater inflater;
    private View courseDetailView;
    private TextView courseCode;
    private TextView professor;
    private TextView buildingName;
    private TextView roomNum;
    private TextView days;
    private TextView start_time;
    private TextView end_time;
    private TextView to;


    public CourseDetailDialog(Course current) {
        current_course = current;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        inflater = getActivity().getLayoutInflater();
        courseDetailView = inflater.inflate(R.layout.course_detail_dialog, null);
        builder.setView(courseDetailView);

        courseCode = (TextView) courseDetailView.findViewById(R.id.Couse_code);
        professor = (TextView) courseDetailView.findViewById(R.id.professor);
        buildingName = (TextView) courseDetailView.findViewById(R.id.building);
        roomNum = (TextView) courseDetailView.findViewById(R.id.roomNum);
        days = (TextView) courseDetailView.findViewById(R.id.days);
        start_time = (TextView) courseDetailView.findViewById(R.id.Start_time);
        end_time = (TextView) courseDetailView.findViewById(R.id.End_time);
        to = (TextView) courseDetailView.findViewById(R.id.to);

        if (current_course.getCode() != null) {
            courseCode.setText(current_course.getCode());
        }

        if (current_course.getProf() != null) {
            professor.setText(current_course.getProf());
        }

        if (current_course.getLocation() != null) {
            buildingName.setText(current_course.getLocation());
        }

        if (current_course.getDays() != null) {
            days.setText(current_course.getDays());
        }

        if (current_course.getBeginTime() != null && !current_course.getBeginTime().equals("")) {
            start_time.setText(current_course.getBeginTime());
        } else {
            to.setText("");
        }

        if (current_course.getEndTime() != null && !current_course.getEndTime().equals("")) {
            end_time.setText(current_course.getEndTime());
        } else {
            to.setText("");
        }

        if (current_course.getRoomNum() != null ) {
            roomNum.setText(current_course.getRoomNum());
        }

        return builder.create();
    }


}

