package com.sholo.gimballapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;

public class MainActivity extends ActionBarActivity {

    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;

    private Toast theToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gimbal.setApiKey(this.getApplication(), "API KEY HERE");
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {
                Log.i("INFO", sighting.toString());
                theToast.setText(sighting.toString());
                theToast.show();
            }
        };
        beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);
        beaconManager.startListening();

        showToast("Searching for beacons...");
    }

    private void showToast(String text)
    {
        if(theToast == null)
            theToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);

        theToast.setText(text);
        theToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.stopListening();
    }
}
