package com.cleandroidgroup.gimbal;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by daveshah on 5/7/15.
 */
public class BeaconListFragment extends ListFragment {

    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;
    private ArrayAdapter<String> adapter;

    public static BeaconListFragment getInstance() {
        return new BeaconListFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("No Beacons Found Yet!");
        setupAdapter();
        listenForBeacons();
    }

    private void listenForBeacons() {
        Gimbal.setApiKey(getActivity().getApplication(), "97e35120-0c08-4318-b755-a88326fdc2d9");
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                Log.i("INFO", sighting.toString());
                adapter.add(sighting.toString());
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
        beaconManager.startListening();
    }

    private void setupAdapter() {
        ArrayList<String> list = new ArrayList<>();
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        beaconManager.stopListening();
    }
}
