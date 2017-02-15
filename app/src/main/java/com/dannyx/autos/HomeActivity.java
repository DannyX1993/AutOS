package com.dannyx.autos;


import com.dannyx.autos.ActivitiesTypes.LauncherActivity;
import android.os.Bundle;

public class HomeActivity extends LauncherActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

}
