package com.dannyx.autos.Models.Contracts;

import android.provider.BaseColumns;

public class ManufacturerContract {

    private ManufacturerContract() {}

    public static class ManufacturerTable implements BaseColumns {

        public static final String TABLE_NAME = "manufacturers";

        public static final String COLUMN_ID   = _ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOGO = "logo";
        public static final String COLUMN_BACKGROUND = "background";

    }

}
