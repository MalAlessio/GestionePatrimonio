package com.example.gestionepatrimonio.entities;

public class MovimentType {

    private int idMovimentType;
    private String movimentTypeName;
    private double maxValue;

    public MovimentType()
    {

    }
    public MovimentType(int idMovimentType, String movimentTypeName, double maxValue) {
        this.idMovimentType = idMovimentType;
        this.movimentTypeName = movimentTypeName;
        this.maxValue = maxValue;
    }

    public int getIdMovimentType() {
        return idMovimentType;
    }

    public void setIdMovimentType(int idMovimentType) {
        this.idMovimentType = idMovimentType;
    }

    public String getMovimentTypeName() {
        return movimentTypeName;
    }

    public void setMovimentTypeName(String movimentTypeName) {
        this.movimentTypeName = movimentTypeName;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }


}
