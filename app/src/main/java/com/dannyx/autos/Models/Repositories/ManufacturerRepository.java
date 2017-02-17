package com.dannyx.autos.Models.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dannyx.autos.Models.Contracts.ManufacturerContract.ManufacturerTable;
import com.dannyx.autos.Models.Entities.Manufacturer;

import java.util.ArrayList;

/**
 * Created by danielgonzalez on 15/2/17.
 */

public class ManufacturerRepository extends SQLiteOpenHelper {

    private static final String DB_FILE_EXT = ".db";
    public static final String DB_FILE = ManufacturerTable.TABLE_NAME + DB_FILE_EXT;
    public static final int VERSION_NUM = 1;

    public ManufacturerRepository(Context context) {
        super(context, DB_FILE, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SQL = "CREATE TABLE " + ManufacturerTable.TABLE_NAME + "( " +
                ManufacturerTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ManufacturerTable.COLUMN_NAME + " TEXT, " +
                ManufacturerTable.COLUMN_LOGO + " TEXT, " +
                ManufacturerTable.COLUMN_BACKGROUND + " TEXT " +
                ");";
        db.execSQL(CREATE_SQL);
        insertInitialData(db);
    }

    public void insertInitialData(SQLiteDatabase db) {
        // TODO -> HACER CARGA MEDIANTE FICHERO

        // Alfa Romeo
        ContentValues values = new ContentValues();
        values.put(ManufacturerTable.COLUMN_NAME, "Alfa Romeo");
        values.put(ManufacturerTable.COLUMN_LOGO, "alfaromeo_logo");
        values.put(ManufacturerTable.COLUMN_BACKGROUND, "alfaromeo_bg");

        db.insert(ManufacturerTable.TABLE_NAME, null, values);

        // Ford
        values = new ContentValues();
        values.put(ManufacturerTable.COLUMN_NAME, "Ford");
        values.put(ManufacturerTable.COLUMN_LOGO, "ford_logo");
        values.put(ManufacturerTable.COLUMN_BACKGROUND, "default_bg");

        db.insert(ManufacturerTable.TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_SQL = "DROP TABLE IF EXISTS " + ManufacturerTable.TABLE_NAME;
        db.execSQL(DROP_SQL);
    }

    /**
     * Devuelve el número de resultados
     *
     * @return El número de resultados
     */
    public long count() {
        SQLiteDatabase db = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, ManufacturerTable.TABLE_NAME);
    }

    /**
     * Obtiene todos los registros existentes de los fabricantes
     *
     * @return Todos los registros existentes
     */
    public ArrayList<Manufacturer> getAll() {
        String GETALL_SQL = "SELECT * FROM " + ManufacturerTable.TABLE_NAME + ";";
        ArrayList<Manufacturer> resultsList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(GETALL_SQL, null);
        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(ManufacturerTable.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(ManufacturerTable.COLUMN_NAME));
                String logo = cursor.getString(cursor.getColumnIndex(ManufacturerTable.COLUMN_LOGO));
                String background = cursor.getString(cursor.getColumnIndex(ManufacturerTable.COLUMN_BACKGROUND));
                Manufacturer manufacturer = new Manufacturer(id, name, logo, background);

                resultsList.add(manufacturer);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return resultsList;
    }

    /**
     * Inserta un nuevo resultado
     *
     * @param name Nombre del fabricante
     * @param logo Logo del fabricante
     * @return Id asignado (-1 si se produce un error)
     */
    public long insert(String name, String logo, String background) {

        // Obtiene la DB en modo escritura
        SQLiteDatabase db = getWritableDatabase();

        // Mapa de valores -> parejas nombreColumna:valor
        ContentValues values = new ContentValues();
        values.put(ManufacturerTable.COLUMN_NAME, name);
        values.put(ManufacturerTable.COLUMN_LOGO, logo);
        values.put(ManufacturerTable.COLUMN_BACKGROUND, background);

        // Realiza la inserción
        return db.insert(ManufacturerTable.TABLE_NAME, null, values);
    }

    /**
     * Elimina un fabricante de la tabla
     * @param   id  Identificador del fabricante
     * @return  Número de filas eliminadas
     */
    public int delete(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String whereCondition = ManufacturerTable.COLUMN_ID + " = ?";
        String[] whereArguments = new String[]{ String.valueOf(id) };

        return db.delete(ManufacturerTable.TABLE_NAME, whereCondition, whereArguments);
    }


    /**
     * Recupera un fabricante por ID
     *
     * @param   id Identificador del fabricante
     * @return  fabricante | null
     */
    public Manufacturer getManufacturerByID(long id) {
        String where = ManufacturerTable.COLUMN_ID + "= ?";
        SQLiteDatabase db = getReadableDatabase();
        Manufacturer manufacturer = null;
        Cursor cursor = db.query(
                ManufacturerTable.TABLE_NAME,
                null,
                where,
                new String[]{ Long.toString(id) },
                null, null, null);

        if (cursor.moveToFirst()) {
            int idManufacturer = cursor.getInt(cursor.getColumnIndex(ManufacturerTable.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(ManufacturerTable.COLUMN_NAME));
            String logo = cursor.getString(cursor.getColumnIndex(ManufacturerTable.COLUMN_LOGO));
            String background = cursor.getString(cursor.getColumnIndex(ManufacturerTable.COLUMN_BACKGROUND));

            manufacturer = new Manufacturer(idManufacturer, name, logo, background);
            cursor.close();
        }

        return manufacturer;
    }

}
