package edu.drury.mcs.Dnav.JavaClass;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import edu.drury.mcs.Dnav.Activity.LookUpSchedule;
import edu.drury.mcs.Dnav.FragmentControl.Schedule_Frag;

/**
 * Created by mark93 on 2/20/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.mViewHolder> {

    public final static String EXTRA_SCHETITLE = "edu.drury.mcs.Dnav.SCHETITLE";
    public final static String EXTRA_SCHEID = "edu.drury.mcs.Dnav.SCHEID";
    private LayoutInflater inflater;
    private Context context;
    private Schedule_Frag fragment;
    List<ListInfomation> data = Collections.emptyList();

    public MyAdapter(Context context, List<ListInfomation> data, Schedule_Frag frag) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.fragment=frag;
    }


    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(edu.drury.mcs.Dnav.R.layout.recycler_view_type, parent, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        ListInfomation current = data.get(position);
        holder.title.setText(current.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView title;
        ImageView icon;
        ImageView info;

        public mViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(edu.drury.mcs.Dnav.R.id.listText);
            icon = (ImageView) itemView.findViewById(edu.drury.mcs.Dnav.R.id.listIcon);
            info = (ImageView) itemView.findViewById(edu.drury.mcs.Dnav.R.id.listInfo);
            info.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (view == info) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.inflate(edu.drury.mcs.Dnav.R.menu.schedule_detail);
                popup.setOnMenuItemClickListener(this);
                popup.show();

            } else {
                String id = data.get(this.getAdapterPosition()).ID;
                String title = data.get(this.getAdapterPosition()).title;
                Intent intent = new Intent(context, LookUpSchedule.class);
                Bundle mBundle = new Bundle();
                mBundle.putString(EXTRA_SCHETITLE, title);
                mBundle.putString(EXTRA_SCHEID, id);
                intent.putExtras(mBundle);
                context.startActivity(intent);
            }
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            XMLController xmlController = new XMLController(context);
            String id = data.get(this.getAdapterPosition()).ID;
            List<Schedule> listy = xmlController.getSchedules();

            Schedule currentSche = null;

            for (int i = 0; i < listy.size(); i++) {
                if (listy.get(i).getID().equals(id)) {
                    currentSche = listy.get(i);
                    System.out.println("Found matching ID with Schedule " + currentSche.getName());
                } else {
                    System.out.println("Schedule ID: " + listy.get(i).getID() + " did not match target ID: " + id);
                }
            }

            xmlController.deleteSchedule(currentSche);

            Toast.makeText(icon.getContext(), "Deleted that shit", Toast.LENGTH_SHORT).show();

            fragment.refresh();
            return true;
        }
    }
}
