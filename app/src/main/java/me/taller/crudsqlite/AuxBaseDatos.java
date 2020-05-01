package me.taller.crudsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class AuxBaseDatos extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DE_DATOS = "donaciones",
            NOMBRE_TABLA_DONACIONES = "donaciones";
    private static final int VERSION_BASE_DE_DATOS = 1;

    public AuxBaseDatos(Context context) {
        super(context, NOMBRE_BASE_DE_DATOS, null, VERSION_BASE_DE_DATOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s(_id integer primary key autoincrement,id text unique not null, nombre text, direccion text,barrio text, fecha text)", NOMBRE_TABLA_DONACIONES));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
