package edu.drury.mcs.Dnav.FragmentControl;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.drury.mcs.Dnav.JavaClass.ListInfomation;
import edu.drury.mcs.Dnav.JavaClass.MyAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class Schedule_Frag extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout= inflater.inflate(edu.drury.mcs.Dnav.R.layout.fragment_schedule, container, false);

        FloatingActionButton mFab = (FloatingActionButton) layout.findViewById(edu.drury.mcs.Dnav.R.id.mFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleName_Dialog sDialog = new ScheduleName_Dialog();
                sDialog.show(getActivity().getFragmentManager(),"Add Schedule");
            }
        });


        mRecyclerView = (RecyclerView) layout.findViewById(edu.drury.mcs.Dnav.R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new MyAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

    public List<ListInfomation> getData(){
        List<ListInfomation> data = new ArrayList<>();
/*
        XMLController xml = new XMLController(getActivity());
        List<Schedule> listSchedule = xml.getSchedules();

         for (Schedule s : listSchedule) {
            ListInfomation current = new ListInfomation();
            current.title = s.getName();
            data.add(current);
        }
*/

        String[] title = {"Schedule_Frag 1","Schedule_Frag 2","Schedule_Frag 3","Schedule_Frag 4","Schedule_Frag 5","Schedule_Frag 6","Schedule_Frag 7","Schedule_Frag 8","Schedule_Frag 9","Schedule_Frag 10",
               "Schedule_Frag 11","Schedule_Frag 12","Schedule_Frag 13","Schedule_Frag 14","Schedule_Frag 15","Schedule_Frag 16"};
        for (String aTitle : title) {
            ListInfomation current = new ListInfomation();
            current.title = aTitle;
            data.add(current);
        }


        return data;
    }




}
