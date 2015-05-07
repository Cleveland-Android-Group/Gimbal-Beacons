package com.cleandroidgroup.gimbal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Gimbal;

public class MainActivity extends ActionBarActivity {

    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;

    private Toast theToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gimbal.setApiKey(this.getApplication(), "97e35120-0c08-4318-b755-a88326fdc2d9");
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
