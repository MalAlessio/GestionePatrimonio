package com.example.gestionepatrimonio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperMoviments extends SQLiteOpenHelper {

    public static final String DBNAME="MOVIMENTS";
    public DbHelperMoviments(Context context)
    {
        super(context,DBNAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        generateTable(DbStrings.moviments,DbStrings.movimentsVariables,db);
        generateTable(DbStrings.movimentsType,DbStrings.movimentsTypeVariables,db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion)
    {

    }


    private void generateTable(String[] variables, String[] variablesType,SQLiteDatabase db)
    {
        try
        {
            String nomeTabella = variables[0];
            String queryMovimenti = "CREATE TABLE " + nomeTabella + " (";
            for (int i = 1; i < variables.length; i++) {
                queryMovimenti = queryMovimenti + variables[i] + " "+variablesType[i]+" ";
                if (i < variables.length - 1) {
                    queryMovimenti = queryMovimenti + ",";
                }
            }
            queryMovimenti = queryMovimenti + ")";
            db.execSQL(queryMovimenti);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
