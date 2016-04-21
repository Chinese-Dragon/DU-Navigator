package edu.drury.mcs.Dnav.JavaClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import edu.drury.mcs.Dnav.R;

/**
 * Created by mark on 4/20/16.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    Context context;
    LayoutInflater inflater;
    List<Building> data;

    public MyInfoWindowAdapter(Context context, List<Building> building_Data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = building_Data;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = inflater.inflate(R.layout.customized_marker_info, null);

        TextView building_name = (TextView) view.findViewById(R.id.building_name);

        Building current_building = getSelectedBuilding(marker, data);
        building_name.setText(current_building.getBuilding_name());

        return view;
    }

    public static Building getSelectedBuilding(Marker current_marker, List<Building> data) {
        Building cur = null;

        for (Building i : data) {
            if (current_marker.getPosition().equals(i.getLocation())) {
                cur = i;
            }
        }
        return cur;
    }
}
