package edu.drury.mcs.Dnav.FragmentControl;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    private DnavDBAdapter dbHelper;
    private SQLiteDatabase Dnav_db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_contact_list_final, container, false);

        dbHelper = new DnavDBAdapter(getActivity());
        Dnav_db = dbHelper.getReadOnlyDB();

        contactRecyclerView = (RecyclerView) layout.findViewById(R.id.contact_recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactAdapter = new MyContactAdapter(getActivity(),getData());
        contactRecyclerView.setAdapter(contactAdapter);

        return layout;
    }

    public List<resource> getData() {

        String query_resource = "SELECT resource_id, name, description FROM resources";
        Cursor cursor_resource = Dnav_db.rawQuery(query_resource,null);

        ArrayList<resource> resource_data = new ArrayList<>();

        while(cursor_resource.moveToNext()){
            String parameterArray[] = new String[1];
            parameterArray[0] = String.valueOf(cursor_resource.getInt(0));
            String query_contacts = "SELECT name, phone, email, address FROM contacts WHERE resource_id = ?";
            Cursor cursor_contacts = Dnav_db.rawQuery(query_contacts,parameterArray);
            ArrayList<Contact_Info> contact_data = new ArrayList<>();
            while(cursor_contacts.moveToNext() && cursor_contacts.getPosition() < 2) {
                contact_data.add(new Contact_Info(cursor_contacts.getString(0), cursor_contacts.getString(1), cursor_contacts.getString(2),
                        cursor_contacts.getString(3)));
            }

            if(contact_data.size() == 1) {
                resource newRes = new resource(cursor_resource.getString(1), cursor_resource.getString(2), contact_data.get(0));
                resource_data.add(newRes);
            }
            if(contact_data.size() >= 2) {
                resource newRes = new resource(cursor_resource.getString(1), cursor_resource.getString(2), contact_data.get(0), contact_data.get(1));
                resource_data.add(newRes);
            }

        }


        return resource_data;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
