package com.cleandroidgroup.gimbal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

import org.w3c.dom.Text;

import java.util.Dictionary;
import java.util.HashMap;

public class MainActivity extends ActionBarActivity {
    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;

    private ViewGroup rootView;
    private HashMap<String, ViewGroup> beaconMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (ViewGroup) findViewById(R.id.root_view);
        beaconMap = new HashMap<>();

        Gimbal.setApiKey(this.getApplication(), "97e35120-0c08-4318-b755-a88326fdc2d9");
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                updateBeaconsForSighting(sighting);
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconManager.stopListening();
    }

    private void updateBeaconsForSighting(BeaconSighting sighting) {
        final ViewGroup viewGroup;
        final String key = sighting.getBeacon().getIdentifier();

        if(!beaconMap.containsKey(key))
        {
            viewGroup = (ViewGroup) LayoutInflater.from(MainActivity.this).inflate(R.layout.sensor_row, null);
            ((TextView) viewGroup.findViewById(R.id.sensor_name)).setText(sighting.getBeacon().getName());
            beaconMap.put(key, viewGroup);
            rootView.addView(viewGroup);
        } else
            viewGroup = beaconMap.get(key);

        ((TextView)viewGroup.findViewById(R.id.sensor_detail)).setText(
                String.format("RSSI: %s; Temp: %s; Battery: %s",
                        sighting.getRSSI(),
                        sighting.getBeacon().getTemperature(),
                        sighting.getBeacon().getBatteryLevel()));
    }
}