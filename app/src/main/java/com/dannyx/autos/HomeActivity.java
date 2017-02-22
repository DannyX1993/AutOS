package com.dannyx.autos;


import com.dannyx.autos.ActivitiesTypes.LauncherActivity;
import com.dannyx.autos.Config.Config;
import com.dannyx.autos.Models.Entities.Manufacturer;
import com.dannyx.autos.Models.Repositories.ManufacturerRepository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HomeActivity extends LauncherActivity {

    private ManufacturerRepository db;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPreferences = getSharedPreferences(Config.PREFS_FILE, Context.MODE_PRIVATE);

        db = new ManufacturerRepository(getApplicationContext());
        Manufacturer manufacturer = db.getManufacturerByID(sharedPreferences.getInt(Config.PREF_MYMANUFACTURER, 0));

        ImageView image = (ImageView) findViewById(R.id.carlogo);
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.activity_main);

        try {
            int logoResourceId = R.drawable.class.getField(manufacturer.get_logo()).getInt(null);
            int bgResourceId = R.drawable.class.getField(manufacturer.get_background()).getInt(null);
            image.setImageResource(logoResourceId);
            relativeLayout.setBackgroundResource(bgResourceId);

            sharedPreferences.edit().putInt(Config.PREF_MYMANUFACTURER, 0).commit();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
