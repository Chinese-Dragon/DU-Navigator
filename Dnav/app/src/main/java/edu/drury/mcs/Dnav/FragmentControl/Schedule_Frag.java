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
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Schedule_Frag extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private View layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(edu.drury.mcs.Dnav.R.layout.fragment_schedule, container, false);

        FloatingActionButton mFab = (FloatingActionButton) layout.findViewById(edu.drury.mcs.Dnav.R.id.mFab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleName_Dialog sDialog = new ScheduleName_Dialog();
                sDialog.show(getActivity().getFragmentManager(), "Add Schedule");
            }
        });


        mRecyclerView = (RecyclerView) layout.findViewById(R.id.my_recycler_view);
        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // specify an adapter
        mAdapter = new MyAdapter(getActivity(), getData(), Schedule_Frag.this);
        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

    public List<ListInfomation> getData() {
        List<ListInfomation> data = new ArrayList<>();

        XMLController xml = new XMLController(getActivity());
        List<Schedule> listSchedule = xml.getSchedules();

        for (Schedule s : listSchedule) {
            ListInfomation current = new ListInfomation();
            current.title = s.getName();
            current.ID = s.getID();
            data.add(current);
        }

        return data;
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();

    }

    public void refresh(){
        mRecyclerView = (RecyclerView) layout.findViewById(edu.drury.mcs.Dnav.R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // specify an adapter
        mAdapter = new MyAdapter(getActivity(), getData(), Schedule_Frag.this);
        mRecyclerView.setAdapter(mAdapter);
    }
}
