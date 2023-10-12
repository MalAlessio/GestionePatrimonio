package com.example.gestionepatrimonio.budget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.gestionepatrimonio.R;
import com.example.gestionepatrimonio.entities.Budget;
import com.example.gestionepatrimonio.entities.Moviments;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends BaseAdapter {

    private List<Budget> budgetList=new ArrayList<Budget>();
    private Context context;

    public BudgetAdapter(Context context,List<Budget> budgetList)
    {
        this.budgetList=budgetList;
        this.context=context;
    }

    @Override
    public int getCount() {
        try
        {
            int movimentCount=budgetList.size();
            return movimentCount;
        }catch (Exception e){
            System.out.println("Errore in BudgetAdapter.getCount()");
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return  this.budgetList.get(position);
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
                Budget budget=(Budget) getItem(position);
                convertView= LayoutInflater.from(context).inflate(R.layout.budget_row,null);
                TextView txt;
                txt= convertView.findViewById(R.id.category_type_text);
                txt.setText(budget.getMovimentType().getMovimentTypeName());
                txt= convertView.findViewById(R.id.value_text);
                txt.setText(budget.getRemainValue());
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