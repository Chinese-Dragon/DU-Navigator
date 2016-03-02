package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
import android.content.Intent;
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

/**
 * Created by mark93 on 2/20/2016.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.mViewHolder>  {

    public final static String EXTRA_MESSAGE = "edu.drury.mcs.Dnav.MESSAGE";
    private LayoutInflater inflater;
    private Context context;
    List<ListInfomation> data = Collections.emptyList();

    public MyAdapter(Context context, List<ListInfomation> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= inflater.inflate(edu.drury.mcs.Dnav.R.layout.recycler_view_type, parent, false);
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



    class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
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
                PopupMenu popup = new PopupMenu(view.getContext(),view);
                popup.inflate(edu.drury.mcs.Dnav.R.menu.schedule_detail);
                popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
                popup.show();

            }else {
                String message = data.get(this.getAdapterPosition()).title;
                Intent intent = new Intent(context, LookUpSchedule.class);
                intent.putExtra(EXTRA_MESSAGE, message);
                context.startActivity(intent);
            }
        }


        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            Toast.makeText(icon.getContext(),"Delete that shit", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
