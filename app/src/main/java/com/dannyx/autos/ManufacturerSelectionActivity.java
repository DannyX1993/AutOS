package com.dannyx.autos;

import com.dannyx.autos.ActivitiesTypes.LauncherActivity;
import com.dannyx.autos.Config.Config;
import com.dannyx.autos.Models.Entities.Manufacturer;
import com.dannyx.autos.Models.Repositories.ManufacturerRepository;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ManufacturerSelectionActivity extends LauncherActivity {

    private ManufacturerRepository db;
    private ArrayList<Manufacturer> manufacturers;
    private int currentManufacturerId;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacturer_selection);
        preferences = getSharedPreferences(Config.PREFS_FILE, Context.MODE_PRIVATE);

        try {
            _getManufacturers(); // Obtenemos fabricantes

            if(preferences.getInt(Config.PREF_MYMANUFACTURER, 0) != 0) {
                _goToHomeActivity();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void _goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void previousManufacturer (View target) throws NoSuchFieldException, IllegalAccessException {

        int newId = currentManufacturerId - 2;
        if(newId < 0) newId = manufacturers.size() - 1;

        Manufacturer manufacturer = manufacturers.get(newId);
        Log.i("AutOS", "Fabricante: " + manufacturer);
        _setManufacturerInCarousel(manufacturer);
    }

    public void nextManufacturer (View target) throws NoSuchFieldException, IllegalAccessException {

        int newId = currentManufacturerId;
        if(newId > manufacturers.size() - 1) newId = 0;

        Manufacturer manufacturer = manufacturers.get(newId);
        _setManufacturerInCarousel(manufacturer);
    }

    public void selectManufacturer(View target) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Config.PREF_MYMANUFACTURER, currentManufacturerId);
        editor.commit();
        Toast.makeText(getApplicationContext(), R.string.manufacturer_selected, Toast.LENGTH_SHORT);
        _goToHomeActivity();
    }

    /**
     * Obtiene todos los fabricantes existentes
     *
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void _getManufacturers() throws NoSuchFieldException, IllegalAccessException {
        db = new ManufacturerRepository(getApplicationContext());
        manufacturers = db.getAll();

        _setManufacturerInCarousel(manufacturers.get(0));
    }

    /**
     * Muestra en el carrusel el fabricante pasado como parámetro
     *
     * @param manufacturer - El fabricante
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private void _setManufacturerInCarousel(Manufacturer manufacturer) throws NoSuchFieldException, IllegalAccessException {

        currentManufacturerId = manufacturer.get_id();

        ImageView manufacturerLogoImageView = (ImageView) findViewById(R.id.current_manufacturer_logo);
        TextView manufacturerNameTextView = (TextView) findViewById(R.id.current_manufacturer_name);

        int logoResourceId = R.drawable.class.getField(manufacturer.get_logo()).getInt(null);
        manufacturerLogoImageView.setImageResource(logoResourceId);
        manufacturerNameTextView.setText(manufacturer.get_name());
    }
}
