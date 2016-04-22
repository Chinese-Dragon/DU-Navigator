package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.drury.mcs.Dnav.R;

/**
 * Created by mark on 4/20/16.
 */

public class MyDialogAdapter extends RecyclerView.Adapter<MyDialogAdapter.sViewHolder> {
    private Context context;
    private List<resource> data;
    private LayoutInflater inflater;
    private View view;

    public MyDialogAdapter(Context context, List<resource> building_services) {
        this.context = context;
        this.data = building_services;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public sViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = inflater.inflate(R.layout.recycler_view_dialog, null);
        return new sViewHolder(view);
    }

    @Override
    public void onBindViewHolder(sViewHolder holder, int position) {
        resource cur_resource = data.get(position);
        holder.service_name.setText(cur_resource.getRes_name());

        bindService2Holder(cur_resource,holder);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindService2Holder(resource cur_res,sViewHolder holder){
        holder.contact_name1.setText(cur_res.getRes_contact1().getContact_name());

        if(cur_res.getRes_contact1().getPhone_num() != null){
            holder.contact_phone1.setText(cur_res.getRes_contact1().getPhone_num());
            Linkify.addLinks(holder.contact_phone1,Linkify.PHONE_NUMBERS);
        }else{
            holder.service_phone1.setVisibility(View.GONE);
        }

        if(cur_res.getRes_contact1().getEmail() != null){
            holder.contact_email1.setText(cur_res.getRes_contact1().getEmail());
            Linkify.addLinks(holder.contact_email1,Linkify.EMAIL_ADDRESSES);
        }else {
            holder.service_email1.setVisibility(View.GONE);
        }

        if (cur_res.getRes_contact1().getAddress() != null){
            holder.contact_address1.setText(cur_res.getRes_contact1().getAddress());
            Linkify.addLinks(holder.contact_address1,Linkify.MAP_ADDRESSES);
        }else{
            holder.service_address1.setVisibility(View.GONE);
        }


        if(cur_res.getRes_contact2() == null){
            holder.contact2.setVisibility(View.GONE);
        }else{
            holder.contact_name2.setText(cur_res.getRes_contact2().getContact_name());
            if (cur_res.getRes_contact2().getPhone_num() != null){
                holder.contact_phone2.setText(cur_res.getRes_contact2().getPhone_num());
                Linkify.addLinks(holder.contact_phone2,Linkify.PHONE_NUMBERS);
            }else {
                holder.service_phone2.setVisibility(View.GONE);
            }

            if (cur_res.getRes_contact2().getEmail() != null){
                holder.contact_email2.setText(cur_res.getRes_contact2().getEmail());
                Linkify.addLinks(holder.contact_email2,Linkify.EMAIL_ADDRESSES);
            }else {
                holder.service_email2.setVisibility(View.GONE);
            }

            if (cur_res.getRes_contact1().getAddress() != null){
                holder.contact_address2.setText(cur_res.getRes_contact2().getAddress());
                Linkify.addLinks(holder.contact_address2,Linkify.MAP_ADDRESSES);
            }else{
                holder.service_address2.setVisibility(View.GONE);
            }
        }
    }

    class sViewHolder extends RecyclerView.ViewHolder {
        private static final int DURATION = 250;
        private ImageView toggle;
        private CardView card;

        //linear views
        private ViewGroup linearShowMore;
        private ViewGroup contact2;
        private ViewGroup service_phone1;
        private ViewGroup service_phone2;
        private ViewGroup service_email1;
        private ViewGroup service_email2;
        private ViewGroup service_address1;
        private ViewGroup service_address2;

        //card content
        private TextView service_name;
        private TextView contact_name1;
        private TextView contact_phone1;
        private TextView contact_email1;
        private TextView contact_address1;
        private TextView contact_address2;

        private TextView contact_name2;
        private TextView contact_phone2;
        private TextView contact_email2;

        public sViewHolder(View itemView) {
            super(itemView);

            toggle = (ImageView) view.findViewById(R.id.toggle2);
            card = (CardView) view.findViewById(R.id.card2);
            linearShowMore = (ViewGroup) view.findViewById(R.id.linearShowMore);
            contact2 = (ViewGroup) view.findViewById(R.id.contact2);
            service_phone1 = (ViewGroup) view.findViewById(R.id.service_phone1);
            service_phone2 = (ViewGroup) view.findViewById(R.id.service_phone2);
            service_email1 = (ViewGroup) view.findViewById(R.id.service_email1);
            service_email2 = (ViewGroup) view.findViewById(R.id.service_email2);
            service_address1 = (ViewGroup) view.findViewById(R.id.service_address1);
            service_address2 = (ViewGroup) view.findViewById(R.id.service_address2);


            //card content
            service_name = (TextView) view.findViewById(R.id.service_name);
            contact_name1 = (TextView) view.findViewById(R.id.contactName1);
            contact_phone1 = (TextView) view.findViewById(R.id.sPhone1);
            contact_email1 = (TextView) view.findViewById(R.id.sMail1);
            contact_address1 = (TextView) view.findViewById(R.id.sAddress1);
            contact_name2 = (TextView) view.findViewById(R.id.contactName2);
            contact_phone2 = (TextView) view.findViewById(R.id.sPhone2);
            contact_email2 = (TextView) view.findViewById(R.id.sMail2);
            contact_address2 = (TextView) view.findViewById(R.id.sAddress2);

            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (linearShowMore.getVisibility() == View.GONE) {
                        ExpandAndCollapseViewUtil.expand(linearShowMore, DURATION);
                        toggle.setImageResource(R.drawable.ic_down);
                        rotate(-180.0f);
                    } else {
                        ExpandAndCollapseViewUtil.collapse(linearShowMore, DURATION);
                        toggle.setImageResource(R.drawable.ic_up);
                        rotate(180.0f);
                    }
                }
            });

            card.getPreventCornerOverlap();
        }

        public void rotate(float angle) {
            Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setFillAfter(true);
            animation.setDuration(DURATION);
            toggle.startAnimation(animation);
        }
    }
}
