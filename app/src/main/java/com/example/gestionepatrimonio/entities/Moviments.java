package com.example.gestionepatrimonio.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Moviments {

    private int idMoviment;
    private Date movimentDate;
    private int idMovimentType;
    private double value;

    private final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd", Locale.ITALIAN);
    private final SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);


    public Moviments()
    {
    }
    public Moviments(int idMoviment, Date movimentDate, int idMovimentType, double value)
    {
        this.idMoviment=idMoviment;
        this.movimentDate = movimentDate;
        this.idMovimentType = idMovimentType;
        this.value = value;
    }
    public Moviments(Date movimentDate, int idMovimentType, double value)
    {
        this.movimentDate = movimentDate;
        this.idMovimentType = idMovimentType;
        this.value = value;
    }


    public int getIdMoviment() {
        return idMoviment;
    }

    public void setIdMoviment(int idMoviment) {
        this.idMoviment = idMoviment;
    }

    public Date getMovimentDate() {
        return movimentDate;
    }
    public String getMovimentDateString()
    {
        String string = simpleDateFormat.format(this.movimentDate);
        return string;
    }
    public String getMovimentDateReadableString()
    {
        String string = simpleDateFormat1.format(this.movimentDate);
        return string;
    }

    public void setMovimentDate(Date movimentDate) {
        this.movimentDate = movimentDate;
    }

    public int getIdMovimentType() {
        return idMovimentType;
    }

    public void setIdMovimentType(int idMovimentType) {
        this.idMovimentType = idMovimentType;
    }

    public double getValue() {
        return value;
    }
    public String getValueString()
    {
        String string = "€ "+this.value;
        return string;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
