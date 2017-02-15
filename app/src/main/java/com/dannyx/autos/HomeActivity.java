package com.dannyx.autos;


import com.dannyx.autos.ActivitiesTypes.LauncherActivity;
import com.dannyx.autos.Models.Entities.Manufacturer;
import com.dannyx.autos.Models.Repositories.ManufacturerRepository;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class HomeActivity extends LauncherActivity {

    private ManufacturerRepository db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new ManufacturerRepository(getApplicationContext());
        //ArrayList<Manufacturer> manufacturers = db.getAll();
        long numFilas = db.count();
        Manufacturer manufacturer = db.getManufacturerByID(1);

        ImageView image = (ImageView) findViewById(R.id.carlogo);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        try {
            int logoResourceId = R.drawable.class.getField(manufacturer.get_logo()).getInt(null);
            int bgResourceId = R.drawable.class.getField(manufacturer.get_background()).getInt(null);
            image.setImageResource(logoResourceId);
            relativeLayout.setBackgroundResource(bgResourceId);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Log.i("AutoOS", "Num. filas: " + numFilas + "\nRegistro 1: " + manufacturer.get_name());
    }

}
