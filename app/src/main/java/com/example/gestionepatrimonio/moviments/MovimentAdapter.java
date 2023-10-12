package com.example.gestionepatrimonio.moviments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gestionepatrimonio.R;
import com.example.gestionepatrimonio.database.DbManagerMoviments;
import com.example.gestionepatrimonio.entities.Moviments;
import com.example.gestionepatrimonio.services.MathOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MovimentAdapter extends BaseAdapter {

    private ArrayList<Moviments> movimentsList=null;
    private Context context=null;
    private DbManagerMoviments dbManagerMoviments;

    public MovimentAdapter(Context context,HashMap<Integer,Moviments> movimentsHashMap)
    {
        ArrayList<Moviments> movimentsArrayList=new ArrayList<>();
        for (Integer codice:movimentsHashMap.keySet()) {
            movimentsArrayList.add(movimentsHashMap.get(codice));
        }
        this.movimentsList=movimentsArrayList;
        this.context=context;
        this.dbManagerMoviments=new DbManagerMoviments(context);


    }

    public interface setOnClickListener(movimentiAdapter.OnClickListener onClickListener) {
        void onClick(int position, Moviments model);
    }


    @Override
    public int getCount() {
        try
        {
            int movimentCount=movimentsList.size();
            return movimentCount;
        }catch (Exception e){
            System.out.println("Errore in MovimentAdapter.getCount()");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return movimentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try{
            if (convertView==null)
            {
                this.dbManagerMoviments=new DbManagerMoviments(context);
                Moviments moviments=(Moviments) getItem(position);
                convertView= LayoutInflater.from(context).inflate(R.layout.moviment_row,null);
                TextView txt;
                txt= convertView.findViewById(R.id.id_moviment_txt);
                txt.setText(String.valueOf(moviments.getIdMoviment()));
                txt= convertView.findViewById(R.id.moviment_type_text);
                String movimentTypeString= dbManagerMoviments.getMovimentById(moviments.getIdMovimentType()).getMovimentTypeName();
                txt.setText(movimentTypeString);
                txt= convertView.findViewById(R.id.moviment_date_text);
                txt.setText(moviments.getMovimentDateReadableString());
                txt= convertView.findViewById(R.id.moviment_value_text);
                String movimentValueString= "€ " + MathOperation.decimalTwoDigit(MathOperation.roundTwo(moviments.getValue()));
                txt.setText(movimentValueString);
            }
        }
        catch (Exception e)
        {
            System.out.println("Errore in MovimentAdapter.getView()");
            e.printStackTrace();
        }
        return convertView;
    }
}