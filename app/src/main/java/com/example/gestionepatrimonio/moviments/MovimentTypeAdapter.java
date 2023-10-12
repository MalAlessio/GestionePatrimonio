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
import com.example.gestionepatrimonio.entities.MovimentType;
import com.example.gestionepatrimonio.entities.Moviments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovimentTypeAdapter extends BaseAdapter {

    private final HashMap<Integer,MovimentType> movimentTypeList;
    private final Context context;

    public MovimentTypeAdapter(Context context, HashMap<Integer,MovimentType> movimentTypeList)
    {
        this.movimentTypeList=movimentTypeList;
        this.context=context;
    }

    @Override
    public int getCount(){
        try{
            int value=this.movimentTypeList.size();
            return value;
        }catch (Exception e)
        {
            System.out.println("Errore MovimentTypeAdapter.getCount()");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Object getItem(int position)
    {
        ArrayList<MovimentType> movimentTypeArrayList=new ArrayList<>(this.movimentTypeList.values());
        return movimentTypeArrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View view, ViewGroup viewGroup){
        if(view==null)
        {
            view= LayoutInflater.from(context).inflate(R.layout.moviment_type_row,null);
        }
        MovimentType movimentType=(MovimentType) getItem(position);
        TextView txt=view.findViewById(R.id.id_moviment_type_text);
        txt.setText(String.valueOf(movimentType.getIdMovimentType()));
        txt=view.findViewById(R.id.moviment_type_text);
        txt.setText(" - "+movimentType.getMovimentTypeName());
        return view;
    }
}