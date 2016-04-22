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

import edu.drury.mcs.Dnav.JavaClass.DnavDBAdapter;
import edu.drury.mcs.Dnav.JavaClass.FAQ;
import edu.drury.mcs.Dnav.JavaClass.MyFAQAdapter;
import edu.drury.mcs.Dnav.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQ_Fragment extends Fragment {

    private  View layout;
    private RecyclerView.Adapter FAQAdapter;
    private RecyclerView FAQRecyclerview;
    private DnavDBAdapter dbHelper;
    private SQLiteDatabase Dnav_db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_faq_, container, false);

        dbHelper = new DnavDBAdapter(getActivity());
        Dnav_db = dbHelper.getReadOnlyDB();

        FAQRecyclerview = (RecyclerView) layout.findViewById(R.id.faq_recycler_view);
        FAQRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        FAQAdapter = new MyFAQAdapter(getActivity(),getData());
        FAQRecyclerview.setAdapter(FAQAdapter);

        return layout;
    }

    public List<FAQ> getData(){
        List<FAQ> FAQ_data= new ArrayList<>();

        String query_faq = "SELECT name, url, faqs_id FROM faqs";
        Cursor cursor_faq= Dnav_db.rawQuery(query_faq,null);

        while(cursor_faq.moveToNext()){
            FAQ_data.add(new FAQ(cursor_faq.getString(0),cursor_faq.getString(1),cursor_faq.getInt(2)));
        }

        return FAQ_data;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
