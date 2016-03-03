package edu.drury.mcs.Dnav.JavaClass;

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

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import edu.drury.mcs.Dnav.Activity.LookUpSchedule;
import edu.drury.mcs.Dnav.R;

/**
 * Created by ksmith034 on 3/2/2016.
 */
public class MyCourseAdapter extends RecyclerView.Adapter<MyCourseAdapter.mViewHolder> implements Serializable {

    private LayoutInflater inflater;
    private Context context;
    private LookUpSchedule schedActivity;
    List<Course> data = Collections.emptyList();
    private Schedule schedule;

    public MyCourseAdapter(Context context, List<Course> data, LookUpSchedule schedActivity, Schedule sched) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.schedActivity = schedActivity;
        this.schedule = sched;
    }

    @Override
    public MyCourseAdapter.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_recycler_view_course, parent, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        Course currentCourse = data.get(position);
        holder.title.setText(currentCourse.getName());
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
                String id = data.get(this.getAdapterPosition()).getID();
                String title = data.get(this.getAdapterPosition()).getName();
//                Intent intent = new Intent(context, LookUpSchedule.class);
//                Bundle mBundle = new Bundle();
//                mBundle.putString(EXTRA_SCHETITLE, title);
//                mBundle.putString(EXTRA_SCHEID, id);
//                intent.putExtra(EXTRA_SCHETITLE, title);
//                context.startActivity(intent);
            }
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            
            schedule.removeCourseByID(data.get(this.getAdapterPosition()).getID());

            XMLController xml = new XMLController(context);

            xml.editSchedule(schedule);

            schedActivity.refresh();

            Toast.makeText(icon.getContext(), "Deleted that shit", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
