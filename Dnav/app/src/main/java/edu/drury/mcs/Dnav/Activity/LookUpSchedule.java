package edu.drury.mcs.Dnav.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import edu.drury.mcs.Dnav.JavaClass.Course;
import edu.drury.mcs.Dnav.JavaClass.MyAdapter;
import edu.drury.mcs.Dnav.JavaClass.MyCourseAdapter;
import edu.drury.mcs.Dnav.JavaClass.Schedule;
import edu.drury.mcs.Dnav.JavaClass.XMLController;
import edu.drury.mcs.Dnav.R;

public class LookUpSchedule extends AppCompatActivity implements View.OnClickListener, Serializable{

    private XMLController xml;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public final static String EXTRA_CURRENTSCHE = "edu.drury.mcs.Dnav.CURRENTSCHE";
    private Schedule currentSche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(edu.drury.mcs.Dnav.R.layout.activity_look_up_schedule);

        String scheTitle = getIntent().getExtras().getString(MyAdapter.EXTRA_SCHETITLE);
        String scheID = getIntent().getExtras().getString(MyAdapter.EXTRA_SCHEID);

        Toolbar toolbar = (Toolbar) findViewById(edu.drury.mcs.Dnav.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(scheTitle);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        xml = new XMLController(this);

        List<Schedule> listy = xml.getSchedules();

        for (int i = 0; i < listy.size(); i++) {
            if (listy.get(i).getID().equals(scheID)) {
                currentSche = listy.get(i);
                System.out.println("Found matching ID with Schedule " + currentSche.getName());
            } else {
                System.out.println("Schedule ID: " + listy.get(i).getID() + " did not match target ID: " + scheID);
            }
        }


        FloatingActionButton mFab = (FloatingActionButton) findViewById(edu.drury.mcs.Dnav.R.id.mFab);
        assert mFab != null;
        mFab.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_course);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mAdapter = new MyCourseAdapter(this, currentSche.getCourses(), LookUpSchedule.this, currentSche, getFragmentManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == edu.drury.mcs.Dnav.R.id.action_settings) {
            return true;
        } else if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, GenerateCourse.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_CURRENTSCHE, currentSche);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();

        refresh();

    }

    public void refresh(){

        //xml = new XMLController(this);

        List<Schedule> listy = xml.getSchedules();

        String scheID = currentSche.getID();

        for (int i = 0; i < listy.size(); i++) {
            if (listy.get(i).getID().equals(scheID)) {
                this.currentSche = listy.get(i);
                System.out.println("Found matching ID with Schedule " + currentSche.getName());
            } else {
                System.out.println("Schedule ID: " + listy.get(i).getID() + " did not match target ID: " + scheID);
            }
        }

        mRecyclerView = (RecyclerView) findViewById(edu.drury.mcs.Dnav.R.id.recycler_view_course);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        // specify an adapter
        mAdapter = new MyCourseAdapter(this, currentSche.getCourses(), LookUpSchedule.this, currentSche, getFragmentManager());
        mRecyclerView.setAdapter(mAdapter);

        List<Course> list = currentSche.getCourses();

        for(int i = 0; i<list.size(); i++)
        {
            System.out.println(list.get(i).getName());
        }

        System.out.println("I AM SO REFRESHED");

    }
}
