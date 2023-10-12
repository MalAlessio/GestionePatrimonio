package com.example.gestionepatrimonio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gestionepatrimonio.budget.BudgetAdapter;
import com.example.gestionepatrimonio.database.DbManagerMoviments;
import com.example.gestionepatrimonio.entities.Budget;
import com.example.gestionepatrimonio.entities.Moviments;
import com.example.gestionepatrimonio.moviments.AddMovimentActivity;
import com.example.gestionepatrimonio.moviments.MovimentActivity;
import com.example.gestionepatrimonio.moviments.MovimentAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BudgetAdapter budgetAdapter;
    private DbManagerMoviments dbManagerMoviments;
    private ListView listView;
    private TextView totalValueText;
    private Button openMoviment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.dbManagerMoviments=new DbManagerMoviments(this);
        this.listView=findViewById(R.id.budget_list);

        this.openMoviment=findViewById(R.id.moviment_button);
        this.openMoviment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMovimentActivity();
            }
        });

        this.totalValueText=findViewById(R.id.total_value);

        dataLoad();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        dataLoad();
    }

    private void dataLoad() {
        try
        {
            List<Budget> budgetList = this.dbManagerMoviments.getBudgets();
            this.budgetAdapter=new BudgetAdapter(this,budgetList);
            listView.setAdapter(this.budgetAdapter);
            String totalValue ="€ "+this.dbManagerMoviments.getTotalBudget(budgetList);
            this.totalValueText.setText(totalValue);
        }
        catch (Exception e)
        {
            System.out.println("Errore in MainActivity.dataLoad");
            e.printStackTrace();
        }
    }

    private void openMovimentActivity()
    {
        try
        {
            Intent intent=new Intent(MainActivity.this, MovimentActivity.class);
            startActivity(intent);
        }
        catch (Exception e)
        {
            System.out.println("Errore in MovimentActivity.openMovimentActivity");
            e.printStackTrace();
        }
    }

}