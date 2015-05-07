package com.cleandroidgroup.gimbal;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_placeholder,BeaconListFragment.getInstance())
        .commit();
    }

}
