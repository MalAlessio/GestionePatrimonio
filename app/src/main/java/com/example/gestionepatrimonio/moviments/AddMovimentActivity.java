package com.example.gestionepatrimonio.moviments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.gestionepatrimonio.R;
import com.example.gestionepatrimonio.database.DbManagerMoviments;
import com.example.gestionepatrimonio.entities.MovimentType;
import com.example.gestionepatrimonio.entities.Moviments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddMovimentActivity extends AppCompatActivity {

    private EditText movimentDateText, movimentValueText;
    private Spinner movimentTypeSpinner;
    private Button addButton;

    private DbManagerMoviments dbManagerMoviments;
    private final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd", Locale.ITALIAN);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_moviment);

        this.dbManagerMoviments=new DbManagerMoviments(this);
        this.movimentDateText=findViewById(R.id.date_text);
        this.movimentValueText=findViewById(R.id.value_text);
        this.movimentTypeSpinner=findViewById(R.id.category_spinner);
        this.addButton=findViewById(R.id.add_button);

        //gestione date
        this.movimentDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar= Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddMovimentActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String dayString;
                                if(dayOfMonth<10)
                                {
                                    dayString="0"+dayOfMonth;
                                }else{
                                    dayString= String.valueOf(dayOfMonth);
                                }
                                String monthString;
                                monthOfYear++;
                                if(monthOfYear<10)
                                {
                                    monthString="0"+monthOfYear;
                                }else {
                                    monthString = String.valueOf(monthOfYear);
                                }
                                movimentDateText.setText(dayString+"/"+monthString+"/"+year );

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        MovimentTypeAdapter movimentTypeAdapter= new MovimentTypeAdapter(this,this.dbManagerMoviments.getMovimentType());
        this.movimentTypeSpinner.setAdapter(movimentTypeAdapter);

        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovimentType movimentType=(MovimentType) movimentTypeSpinner.getSelectedItem();
                boolean ctrl=SaveMoviment(movimentDateText.getText().toString(),movimentValueText.getText().toString(),movimentType.getIdMovimentType());
                if(ctrl)
                {
                    closeActivity();
                }
            }
        });
    }

    private boolean SaveMoviment(String dateString,String valueString,int idMovimentType)
    {
        try{
            dateString=dateString.substring(6)+dateString.substring(3,5)+dateString.substring(0,2);
            Date movimentDate= simpleDateFormat.parse(dateString);
            Double movimentValue=Double.parseDouble(valueString);
            Moviments moviment=new Moviments(movimentDate,idMovimentType,movimentValue);
            this.dbManagerMoviments.SaveMoviment(moviment);
        }
        catch (Exception e)
        {
            System.out.println("Errore in AddMovimentActivity.SaveMoviment");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private void closeActivity()
    {
        this.finish();
    }
}