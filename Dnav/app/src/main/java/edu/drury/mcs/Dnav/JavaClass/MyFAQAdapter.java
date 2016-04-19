package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
import android.content.Intent;
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
    private Context context;
    private List<FAQ> data = Collections.emptyList();
    private LayoutInflater inflater;
    private String link;

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
        link = current.getFAQ_link();
        holder.FAQ_Name.setText(current.getFAQ_name());

        holder.FAQ_CARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FAQ_page.class);
                intent.putExtra(EXTRA_LINK,link);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class fViewHolder extends RecyclerView.ViewHolder {
        TextView FAQ_Name;
        CardView FAQ_CARD;
        public fViewHolder(View itemView) {
            super(itemView);
            FAQ_CARD = (CardView) itemView.findViewById(R.id.faq_card);
            FAQ_Name = (TextView) itemView.findViewById(R.id.faq_name);
        }
    }
}
