package edu.drury.mcs.Dnav.FragmentControl;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.drury.mcs.Dnav.JavaClass.Contact_Info;
import edu.drury.mcs.Dnav.JavaClass.DnavDBAdapter;
import edu.drury.mcs.Dnav.JavaClass.MyContactAdapter;
import edu.drury.mcs.Dnav.JavaClass.resource;
import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class contact_list_final extends Fragment {
    private RecyclerView contactRecyclerView;
    private RecyclerView.Adapter contactAdapter;
    private View layout;
    DnavDBAdapter dbHelper;
    private long check_insert_landmarks;
    private long check_insert_resources;
    private long check_insert_contacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_contact_list_final, container, false);

        dbHelper = new DnavDBAdapter(getActivity());


        dbHelper.createDB();


        contactRecyclerView = (RecyclerView) layout.findViewById(R.id.contact_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter = new MyContactAdapter(getActivity(), getData());
        contactRecyclerView.setAdapter(contactAdapter);

        return layout;
    }

    public List<resource> getData() {
        List<resource> data = new ArrayList<>();
        Contact_Info contact = new Contact_Info();
        contact.setEmail("yma004@drury.edu");
        contact.setContact_name("Mark Ma");
        Contact_Info contact1 = new Contact_Info("Dr.Sigman", "(417)-770-0565"
                , "ssigman@drury.edu", "Pearsons Hall, Room 107");
        Contact_Info contact2 = new Contact_Info("Dr.Browning", "(417) 873-7268", "cbrownin@drury.edu", "Pearsons Hall 102");
        resource resource1 = new resource("Computer Science", "Computer Science Professors", contact, contact2);
        data.add(resource1);

        return data;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
