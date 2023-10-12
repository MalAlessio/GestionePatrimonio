package com.example.gestionepatrimonio.moviments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gestionepatrimonio.R;
import com.example.gestionepatrimonio.database.DbManagerMoviments;
import com.example.gestionepatrimonio.entities.MovimentType;
import com.example.gestionepatrimonio.entities.Moviments;
import com.example.gestionepatrimonio.services.MathOperation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovimentActivity extends AppCompatActivity {

    private MovimentAdapter movimentiAdapter;
    private DbManagerMoviments dbManagerMoviments;
    private ListView listView;
    private TextView totalValueText;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviment);

        this.dbManagerMoviments=new DbManagerMoviments(this);
        this.listView=findViewById(R.id.moviment_list);

        this.addButton=findViewById(R.id.add_button);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovimentsActivity();
            }
        });

        this.totalValueText=findViewById(R.id.total_value);

        dataLoad();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private void dataLoad() {
        try
        {
            HashMap<Integer,Moviments> movimentsList = this.dbManagerMoviments.getMoviments(2);
            this.movimentiAdapter=new MovimentAdapter(this,movimentsList);
            listView.setAdapter(this.movimentiAdapter);
            String totalValue ="Spese totali € "+ MathOperation.decimalTwoDigit(MathOperation.changeSign(this.dbManagerMoviments.getTotalValue()));
            this.totalValueText.setText(totalValue);

            this.movimentiAdapter.setOnClickListener(new movimentiAdapter.OnClickListener() {
                @Override
                public void onClick(int position, Moviments model) {

                }
            });
        }
        catch (Exception e)
        {
            System.out.println("Errore in MovimentActivity.dataLoad");
            e.printStackTrace();
        }
    }

    private void openMovimentsActivity()
    {
        try
        {
            Intent intent=new Intent(MovimentActivity.this,AddMovimentActivity.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.println("Errore in MovimentActivity.OpenMovimentsActivity");
            e.printStackTrace();
        }
    }
}