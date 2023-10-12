package com.example.gestionepatrimonio.database;

public class DbStrings {

    //Moviments
    public static final String[] moviments={"moviment","id_moviment","moviment_date","id_moviment_type","value"};
    public static final String[] movimentsVariables={"tab_name","INTEGER PRIMARY KEY","INTEGER","INTEGER","REAL"};

    public static final String[] movimentsType={"moviment_type","id_moviment_type","moviment_type_name","max_value"};
    public static final String[] movimentsTypeVariables={"TAB_NAME","INTEGER PRIMARY KEY","TEXT","REAL"};


}
