package edu.drury.mcs.Dnav.JavaClass;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

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
    private FragmentManager fragmentManager;

    public MyCourseAdapter(Context context, List<Course> data, LookUpSchedule schedActivity, Schedule sched, FragmentManager manager) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.schedActivity = schedActivity;
        this.schedule = sched;
        this.fragmentManager = manager;
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_recycler_view_course, parent, false);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, final int position) {
        Course currentCourse = data.get(position);
        holder.title.setText(currentCourse.getName());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Course selected_Course = data.get(position);
                CourseDetailDialog dialog= new CourseDetailDialog(selected_Course);
                dialog.show(fragmentManager, "show Building Info");
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView title;
        ImageView info;

        public mViewHolder(final View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.course_name);
            info = (ImageView) itemView.findViewById(R.id.course_Delete);

            info.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(), view);
                popup.inflate(edu.drury.mcs.Dnav.R.menu.schedule_detail);
                popup.setOnMenuItemClickListener(this);
                popup.show();
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            
            schedule.removeCourseByID(data.get(this.getAdapterPosition()).getID());

            XMLController xml = new XMLController(context);

            xml.editSchedule(schedule);

            schedActivity.refresh();

            return true;
        }
    }
}
