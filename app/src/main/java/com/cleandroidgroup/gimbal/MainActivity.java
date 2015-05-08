package com.cleandroidgroup.gimbal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
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

    private Toast theToast;
    private ViewGroup rootView;

    private HashMap<String, TextView> beaconMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = (ViewGroup) findViewById(R.id.root_view);

        beaconMap = new HashMap<String, TextView>();

        Gimbal.setApiKey(this.getApplication(), "97e35120-0c08-4318-b755-a88326fdc2d9");
        beaconSightingListener = new BeaconEventListener() {
            @Override
            public void onBeaconSighting(BeaconSighting sighting) {

                TextView tv;
                String key = sighting.getBeacon().getIdentifier();
                if(!beaconMap.containsKey(key))
                {
                    tv = new TextView(MainActivity.this);
                    tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                    beaconMap.put(key, tv);
                    rootView.addView(tv);
                } else
                {
                    tv = beaconMap.get(key);
                }

                tv.setText(String.format("Name: %s; Temp: %s; Battery: %s",
                    sighting.getBeacon().getName(),
                    sighting.getBeacon().getTemperature(),
                    sighting.getBeacon().getBatteryLevel()));
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
