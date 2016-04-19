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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_faq_, container, false);

        FAQRecyclerview = (RecyclerView) layout.findViewById(R.id.faq_recycler_view);
        FAQRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        FAQAdapter = new MyFAQAdapter(getActivity(),getData());
        FAQRecyclerview.setAdapter(FAQAdapter);

        return layout;
    }

    public List<FAQ> getData(){
        List<FAQ> FAQ_data= new ArrayList<>();
        FAQ housing = new FAQ("Housing","http://www.drury.edu/housing/housing-frequently-asked-questions/");
        FAQ orientation = new FAQ("Orientation Info","http://www.drury.edu/fye/registration-and-orientation-information-for-new-students/");
        FAQ_data.add(housing);
        FAQ_data.add(orientation);
        return FAQ_data;
    }
}
