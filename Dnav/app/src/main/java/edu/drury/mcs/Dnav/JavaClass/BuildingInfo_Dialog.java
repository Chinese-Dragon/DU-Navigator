package edu.drury.mcs.Dnav.JavaClass;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

import edu.drury.mcs.Dnav.R;

/**
 * Created by mark on 4/20/16.
 */
public class BuildingInfo_Dialog extends DialogFragment {
    private Marker current_marker;
    private List<Building> data;
    private List<resource> building_services;
    private RecyclerView dialogRecycler;
    private RecyclerView.Adapter dialogAdapter;
    private TextView description;
    private Building current_Building;
    private View buildingInfo_view;
    private LayoutInflater inflater;

    public BuildingInfo_Dialog(Marker marker, List<Building> data) {
        this.current_marker = marker;
        this.data = data;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get the layout inflater

        inflater = getActivity().getLayoutInflater();
        buildingInfo_view = inflater.inflate(R.layout.building_dialog, null);
        builder.setView(buildingInfo_view);

        current_Building = MyInfoWindowAdapter.getSelectedBuilding(current_marker, data);
        description = (TextView) buildingInfo_view.findViewById(R.id.building_description);
        description.setText(current_Building.getBuilding_info());

        building_services = current_Building.getResources_list();
        dialogRecycler = (RecyclerView) buildingInfo_view.findViewById(R.id.service_list);
        dialogRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialogAdapter = new MyDialogAdapter(getActivity(), building_services);
        dialogRecycler.setAdapter(dialogAdapter);

        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
