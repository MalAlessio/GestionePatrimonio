package com.example.gestionepatrimonio.entities;

import com.example.gestionepatrimonio.services.MathOperation;

public class Budget {

    private MovimentType movimentType;
    private double actualValue;

    public Budget(MovimentType movimentType, double actualValue) {
        this.movimentType = movimentType;
        this.actualValue = actualValue;
    }

    public MovimentType getMovimentType() {
        return movimentType;
    }

    public void setMovimentType(MovimentType movimentType) {
        this.movimentType = movimentType;
    }

    public double getActualValue() {
        return actualValue;
    }

    public void setActualValue(double actualValue) {
        this.actualValue = actualValue;
    }

    public String getRemainValue()
    {
        String actualValueString=MathOperation.decimalTwoDigit(MathOperation.changeSign(MathOperation.roundTwo(this.actualValue)));
        String maxValueString=MathOperation.decimalTwoDigit(this.movimentType.getMaxValue());
        String remainValueString="€ "+actualValueString+"/"+maxValueString;
        return remainValueString;
    }
}
