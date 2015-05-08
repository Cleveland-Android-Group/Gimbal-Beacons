package com.cleandroidgroup.gimbal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

import java.util.ArrayList;

import retrofit.RestAdapter;

/**
 * Created by daveshah on 5/7/15.
 */
public class BeaconListFragment extends ListFragment {

    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;
    private ArrayAdapter<String> adapter;
    private ClimeAPI api;
    private int horribleCounter;
    private static final int MAX_SENSOR_COUNT = 4;

    public static BeaconListFragment getInstance() {
        return new BeaconListFragment();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("No Beacons Found Yet!");
        setupAdapter();
        listenForBeacons();
        api = createApi();
    }

    private void listenForBeacons() {
        Gimbal.setApiKey(getActivity().getApplication(), "97e35120-0c08-4318-b755-a88326fdc2d9");
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                Log.i("INFO", sighting.toString());

                adapter.add(sighting.toString());
                if(adapter.getCount() > 10)  adapter.clear();
                sendSighting(sighting);
                sighting.getRSSI();

            }
        };

        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
        beaconManager.startListening();
    }

    private void sendSighting(BeaconSighting sighting) {
        Beacon beacon = sighting.getBeacon();
        final SensorReading reading = new SensorReading();
        reading.text = String.format("Temperature: %d", beacon.getTemperature());
        reading.title = beacon.getName();
        reading.moreinfo = String.format("Battery Level: %s Updated: %d",beacon.getBatteryLevel(),sighting.getTimeInMillis());
        reading.auth_token = "YOUR_AUTH_TOKEN";

        new Thread() {
            @Override
            public void run() {
                super.run();
                String sensorToDisplay = String.format("sensor%d",Math.abs(horribleCounter%MAX_SENSOR_COUNT));
                api.sendSensorData(sensorToDisplay,reading);
            }
        }.start();
        horribleCounter++;
    }

    private ClimeAPI createApi() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://clime.herokuapp.com").build();
        return adapter.create(ClimeAPI.class);
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
