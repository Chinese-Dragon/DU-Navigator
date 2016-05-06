package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
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

import java.util.Collections;
import java.util.List;

import edu.drury.mcs.Dnav.R;

/**
 * Created by mark93 on 4/11/2016.
 */
public class MyContactAdapter extends RecyclerView.Adapter<MyContactAdapter.cViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<resource> data = Collections.emptyList();

    public MyContactAdapter(Context context, List<resource> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public cViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_card_type, parent, false);
        return new cViewHolder(view);
    }

    @Override
    public void onBindViewHolder(cViewHolder holder, int position) {
        resource cur_resource = data.get(position);
        holder.res_name.setText(cur_resource.getRes_name());
        holder.description.setText(cur_resource.getRes_description());

        //add service to contact info and set visibility to view that do not have data
        bindRes2Holder(cur_resource,holder);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindRes2Holder(resource cur_resource, cViewHolder holder) {

        //add service to contact info and set visibility to view that do not have data
        holder.contact_name1.setText(cur_resource.getRes_contact1().getContact_name());
        if (cur_resource.getRes_contact1().getPhone_num() != null) {
            holder.contact_phone1.setText(cur_resource.getRes_contact1().getPhone_num());
            Linkify.addLinks(holder.contact_phone1, Linkify.PHONE_NUMBERS);
        } else {
            holder.phoneSection1.setVisibility(View.GONE);
        }

        if (cur_resource.getRes_contact1().getEmail() != null) {
            holder.contact_email1.setText(cur_resource.getRes_contact1().getEmail());
            Linkify.addLinks(holder.contact_email1, Linkify.EMAIL_ADDRESSES);
        } else {
            holder.emailSection1.setVisibility(View.GONE);
        }

        if (cur_resource.getRes_contact1().getAddress() != null) {
            holder.contact_address1.setText(cur_resource.getRes_contact1().getAddress());
            Linkify.addLinks(holder.contact_address1, Linkify.MAP_ADDRESSES);
        } else {
            holder.addressSection1.setVisibility(View.GONE);
        }

        //check if there is contact 2 and assign info if there is
        if (cur_resource.getRes_contact2() == null) {
            holder.divider.setVisibility(View.GONE);
            holder.contact_name2.setVisibility(View.GONE);
            holder.contact_info2.setVisibility(View.GONE);
        } else {
            holder.contact_name2.setText(cur_resource.getRes_contact2().getContact_name());
            if (cur_resource.getRes_contact2().getPhone_num() != null) {
                holder.contact_phone2.setText(cur_resource.getRes_contact2().getPhone_num());
                Linkify.addLinks(holder.contact_phone2, Linkify.PHONE_NUMBERS);
            } else {
                holder.phoneSection2.setVisibility(View.GONE);
            }

            if (cur_resource.getRes_contact2().getEmail() != null) {
                holder.contact_email2.setText(cur_resource.getRes_contact2().getEmail());
                Linkify.addLinks(holder.contact_email2, Linkify.EMAIL_ADDRESSES);
            } else {
                holder.emailSection2.setVisibility(View.GONE);
            }

            if (cur_resource.getRes_contact2().getAddress() != null) {
                holder.contact_address2.setText(cur_resource.getRes_contact2().getAddress());
                Linkify.addLinks(holder.contact_address2, Linkify.MAP_ADDRESSES);
            } else {
                holder.addressSection2.setVisibility(View.GONE);
            }

        }
    }

    class cViewHolder extends RecyclerView.ViewHolder {
        private static final int DURATION = 250;
        private ImageView toggle;
        private CardView card;
        private ViewGroup linearLayoutDetail;
        private ViewGroup contact_info2;
        private ViewGroup phoneSection1;
        private ViewGroup emailSection1;
        private ViewGroup addressSection1;
        private ViewGroup phoneSection2;
        private ViewGroup emailSection2;
        private ViewGroup addressSection2;

        //card content
        private TextView res_name;
        private TextView description;
        private TextView contact_name1;
        private TextView contact_phone1;
        private TextView contact_email1;
        private TextView contact_address1;
        private View divider;
        private TextView contact_name2;
        private TextView contact_phone2;
        private TextView contact_email2;
        private TextView contact_address2;

        public cViewHolder(View view) {
            super(view);

            contact_info2 = (ViewGroup) view.findViewById(R.id.contact2_info);
            phoneSection1 = (ViewGroup) view.findViewById(R.id.phoneSection1);
            emailSection1 = (ViewGroup) view.findViewById(R.id.emailSection1);
            addressSection1 = (ViewGroup) view.findViewById(R.id.addressSection1);
            phoneSection2 = (ViewGroup) view.findViewById(R.id.phoneSection2);
            emailSection2 = (ViewGroup) view.findViewById(R.id.emailSection2);
            addressSection2 = (ViewGroup) view.findViewById(R.id.addressSection2);

            //useful view for onlick
            card = (CardView) view.findViewById(R.id.card1);
            toggle = (ImageView) view.findViewById(R.id.toggle);
            linearLayoutDetail = (ViewGroup) view.findViewById(R.id.lineardetail);

            //attachment for resource heading content
            res_name = (TextView) view.findViewById(R.id.resource_name);
            description = (TextView) view.findViewById(R.id.description);

            //attachment for contact info
            contact_name1 = (TextView) view.findViewById(R.id.contact1_name);
            contact_phone1 = (TextView) view.findViewById(R.id.phoneNum1);
            contact_email1 = (TextView) view.findViewById(R.id.email1);
            contact_address1 = (TextView) view.findViewById(R.id.address1);
            divider = view.findViewById(R.id.contact_divider);
            contact_name2 = (TextView) view.findViewById(R.id.contact2_name);
            contact_phone2 = (TextView) view.findViewById(R.id.phoneNum2);
            contact_email2 = (TextView) view.findViewById(R.id.email2);
            contact_address2 = (TextView) view.findViewById(R.id.address2);


            toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (linearLayoutDetail.getVisibility() == View.GONE) {
                        ExpandAndCollapseViewUtil.expand(linearLayoutDetail, DURATION);
                        toggle.setImageResource(R.drawable.ic_down);
                        rotate(-180.0f);
                    } else {
                        ExpandAndCollapseViewUtil.collapse(linearLayoutDetail, DURATION);
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
