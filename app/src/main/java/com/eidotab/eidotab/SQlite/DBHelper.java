package com.eidotab.eidotab.SQlite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.eidotab.eidotab.Model.Cuentadata;
import com.eidotab.eidotab.Model.DataRoot;
import com.eidotab.eidotab.Model.Menua;
import com.eidotab.eidotab.Model.Plato;
import com.eidotab.eidotab.Model.Tablet;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "eidobdemo00l.db";
    private static final int DATABASE_VERSION = 3;
    private String DB_PATH = null;

    public static DBHelper GetDBHelper(Context context)
    {
        DBHelper dbHelper = new DBHelper(context.getApplicationContext());

        if (!dbHelper.isDataBaseExist())
        {
            dbHelper.deleteAllPlato();
            dbHelper.deleteAllPedido();
            dbHelper.deleteAllTablet();
            dbHelper.deleteAllMenua();
            dbHelper.deleteAllCuenta();
            dbHelper.createDataBase();
        }
        return dbHelper;
    }

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    private void createDataBase()
    {
        boolean isExist = isDataBaseExist();

        if (!isExist)
        {
            this.getReadableDatabase();

            onCreate(this.getWritableDatabase());
        }
    }

    public boolean isDataBaseExist()
    {
        File file = new File(DB_PATH + DATABASE_NAME);

        return file.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Escribir la estructura de la bd: Tablas, ...
        db.execSQL(" CREATE TABLE plato  (_id TEXT primary key, jplato  TEXT); ");
        db.execSQL(" CREATE TABLE cuenta (_id INTEGER primary key, jcuenta TEXT); ");
        db.execSQL(" CREATE TABLE pedido (indice TEXT primary key, jpedido TEXT); ");
        db.execSQL(" CREATE TABLE tablet (nrtab TEXT primary key, jtablet TEXT); ");
        db.execSQL(" CREATE TABLE menua (_id TEXT primary key, jmenua TEXT); ");
        // ....
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Escribir las modificaciones en la bd.
        db.execSQL(" DROP TABLE IF EXISTS plato; ");
        db.execSQL(" DROP TABLE IF EXISTS cuenta; ");
        db.execSQL(" DROP TABLE IF EXISTS pedido; ");
        db.execSQL(" DROP TABLE IF EXISTS tablet; ");
        db.execSQL(" DROP TABLE IF EXISTS menua; ");
        onCreate(db);
    }


    /* IMPLEMENTACIÓN: MÉTODOS CRUD */

    /* TABLA: plato */

    private static final String TABLE_NAME_PLATO = "plato";

    private static final String TABLE_NAME_CUENTA = "cuenta";

    private static final String TABLE_NAME_PEDIDO = "pedido";

    private static final String TABLE_NAME_TABLET = "tablet";

    private static final String TABLE_NAME_MENUA = "menua";

    public boolean addPlato(Plato plato)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(plato);
        contentValues.put("_id", plato.get_id());
        contentValues.put("jplato", json);
        db.insert(TABLE_NAME_PLATO, null, contentValues);

        return true;
    }

    public boolean addCuenta(Cuentadata cuentadata)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(cuentadata);
        contentValues.put("jcuenta", json);
        db.insert(TABLE_NAME_CUENTA, null, contentValues);

        return true;
    }

    public boolean addPedido(DataRoot pedido)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(pedido);
        contentValues.put("indice", pedido.getIdinterno());
        contentValues.put("jpedido", json);
        db.insert(TABLE_NAME_PEDIDO, null, contentValues);

        return true;
    }

    public boolean addMesa(Tablet tablet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(tablet);
        contentValues.put("nrtab", tablet.getNro_tablet());
        contentValues.put("jtablet", json);
        db.insert(TABLE_NAME_TABLET, null, contentValues);
        return true;
    }

    public boolean addMenua(Menua menua)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(menua);
        contentValues.put("_id", menua.getId());
        contentValues.put("jmenua", json);
        db.insert(TABLE_NAME_MENUA, null, contentValues);
        return true;
    }


/*    public boolean updatePlato(Plato plato)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(plato);
        contentValues.put("_id", plato.get_id());
        contentValues.put("jplato", json);
        db.update(TABLE_NAME_PLATO, contentValues, "_id = ?",
                new String[]{ plato.get_id() });

        return true;
    }*/

    public boolean updatePedido(DataRoot dataRoot)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(dataRoot);
        contentValues.put("indice", dataRoot.getIdinterno());
        contentValues.put("jpedido", json);
        db.update(TABLE_NAME_PEDIDO, contentValues, "indice = ?",
                new String[]{ dataRoot.getIdinterno() });

        return true;
    }

/*    public boolean updateTablet(Tablet tablet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(tablet);
        contentValues.put("nrtab", tablet.getNro_tablet());
        contentValues.put("jtablet", json);
        db.update(TABLE_NAME_TABLET, contentValues, "nrtab = ?",
                new String[]{ tablet.getNro_tablet() });

        return true;
    }*/


/*    public boolean deletePlato(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_PLATO, "_id = ?",
                new String[]{ id });

        return true;
    }*/

/*    public boolean deletePedido(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_PEDIDO, "indice = ?",
                new String[]{ id });

        return true;
    }*/

/*    public boolean deleteTablet(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_TABLET, "nrtab = ?",
                new String[]{ id });

        return true;
    }*/

    public boolean deleteAllPlato()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_PLATO, "",
                new String[]{  });

        return true;
    }

    public boolean deleteAllCuenta()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_CUENTA, "",
               new String[]{ });

        return true;
    }

    public boolean deleteAllPedido()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_PEDIDO, "",
                new String[]{  });

        return true;
    }

    public boolean deleteAllTablet()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_TABLET, "",
                new String[]{  });

        return true;
    }

    public boolean deleteAllMenua()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_MENUA, "",
                new String[]{  });

        return true;
    }


/*    public Plato getPlato(String id)
    {
        Plato plato = new Plato();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PLATO + " WHERE _id = '" + id + "'", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jplato")));

            plato  = gson.fromJson(sacadata, Plato.class);

            cursor.moveToNext();
        }

        cursor.close();
        return plato;
    }*/

    public DataRoot getPedido(String idin)
    {
        DataRoot pedido = new DataRoot();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PEDIDO + " WHERE indice= '" + idin + "'", null);

        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast())
            {

                String sacadata = (cursor.getString(cursor.getColumnIndex("jpedido")));

                pedido = gson.fromJson(sacadata, DataRoot.class);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
            db.close();

        }





        return pedido;
    }

/*    public Tablet getTablet(String nutab)
    {
        Tablet tablet = new Tablet();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_TABLET + " WHERE nrtab= '" + nutab + "'", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jtablet")));

            tablet = gson.fromJson(sacadata, Tablet.class);

            cursor.moveToNext();
        }

        cursor.close();
        return tablet;
    }*/

    public ArrayList<Plato> listPlatos()
    {
        ArrayList<Plato> lista = new ArrayList<>();

        Plato plato;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PLATO, null);

        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast())
            {
                //plato = new Plato();

                String sacadata = (cursor.getString(cursor.getColumnIndex("jplato")));

                plato  = gson.fromJson(sacadata, Plato.class);

                lista.add(plato);


                cursor.moveToNext();
            }
        } finally {
            cursor.close();
            db.close();
        }


        return lista;
    }

    public ArrayList<Cuentadata> listCuenta()
    {
        ArrayList<Cuentadata> lista = new ArrayList<>();

        Cuentadata cuentadata;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_CUENTA, null);

        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast())
            {
              // cuentadata = new Cuentadata();

                String sacadata = (cursor.getString(cursor.getColumnIndex("jcuenta")));

                cuentadata  = gson.fromJson(sacadata, Cuentadata.class);

                lista.add(cuentadata);


                cursor.moveToNext();
            }
        } finally {

            cursor.close();
            db.close();

        }


        return lista;
    }


    /*      public ArrayList<DataRoot> listPedidos()
          {
              ArrayList<DataRoot> lista = new ArrayList<>();

              DataRoot pedido;

              Gson gson = new Gson();

              SQLiteDatabase db = this.getReadableDatabase();

              Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PEDIDO, null);

              cursor.moveToFirst();

              while (!cursor.isAfterLast())
              {
                  pedido = new DataRoot();

                  String sacadata = (cursor.getString(cursor.getColumnIndex("jpedido")));

                  pedido  = gson.fromJson(sacadata, DataRoot.class);

                  lista.add(pedido);

                  cursor.moveToNext();
              }

              return lista;
          }*/
/*    public DataRoot listPedidos()
    {
        // DataRoot lista = new DataRoot();

        DataRoot pedido = new DataRoot();

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PEDIDO, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jpedido")));

            pedido  = gson.fromJson(sacadata, DataRoot.class);

            cursor.moveToNext();
        }

        return pedido;
    }*/
    public DataRoot listPedidos()
    {
        DataRoot orden = new DataRoot();

        DataRoot pedido;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_PEDIDO, null);

        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast())
            {
                //pedido = new DataRoot();

                String sacadata = (cursor.getString(cursor.getColumnIndex("jpedido")));

                pedido  = gson.fromJson(sacadata, DataRoot.class);

                orden = (pedido);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
            db.close();

        }


        return orden;
    }

    public Tablet listTablet()
    {
        Tablet tablet = new Tablet();

        Tablet tabla;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_TABLET, null);

        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast())
            {
                //tabla = new Tablet();

                String sacadata = (cursor.getString(cursor.getColumnIndex("jtablet")));

                tabla  = gson.fromJson(sacadata, Tablet.class);

                tablet = (tabla);

                cursor.moveToNext();
            }
        } finally {
            cursor.close();
            db.close();

        }


        return tablet;
    }

    public Menua listMenua()
    {
        Menua menua = new Menua();

        Menua menu;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_MENUA, null);

        cursor.moveToFirst();

        try {
            while (!cursor.isAfterLast())
            {
                //menu = new Menua();

                String sacadata = (cursor.getString(cursor.getColumnIndex("jmenua")));

                menu  = gson.fromJson(sacadata, Menua.class);

                menua= (menu);

                cursor.moveToNext();
            }
        } finally {

            cursor.close();
            db.close();

        }


        return menua;
    }


}
