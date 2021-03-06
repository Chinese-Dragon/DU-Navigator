package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.drury.mcs.Dnav.Activity.FAQ_page;
import edu.drury.mcs.Dnav.R;

/**
 * Created by yma004 on 4/18/2016.
 */

public class MyFAQAdapter extends RecyclerView.Adapter<MyFAQAdapter.fViewHolder> {

    public final static String EXTRA_LINK = "edu.drury.mcs.Dnav.FAQLINK";
    public final static String EXTRA_FAQNAME = "edu.drury.mcs.Dnav.FAQNAME";
    private Context context;
    private List<FAQ> data = Collections.emptyList();
    private LayoutInflater inflater;
    private String link;
    private String name;

    public  MyFAQAdapter(Context context, List<FAQ> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public fViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_faq_type,parent,false);
        return new fViewHolder(view);
    }

    @Override
    public void onBindViewHolder(fViewHolder holder, int position) {
        FAQ current = data.get(position);
        holder.FAQ_Name.setText(current.getFAQ_name());




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class fViewHolder extends RecyclerView.ViewHolder {
        TextView FAQ_Name;

        public fViewHolder(View itemView) {
            super(itemView);
            FAQ_Name = (TextView) itemView.findViewById(R.id.faq_name);

            FAQ_Name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    link = data.get(getAdapterPosition()).getFAQ_link();
                    name = data.get(getAdapterPosition()).getFAQ_name();

                    Intent intent = new Intent(context, FAQ_page.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRA_FAQNAME,name);
                    bundle.putString(EXTRA_LINK,link);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
