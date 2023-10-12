package com.example.gestionepatrimonio.services;

import java.text.DecimalFormat;

public class MathOperation {

    public static double roundTwo(double value)
    {
        value=value*100;
        double roundValue=Math.round(value);
        roundValue=roundValue/100;
        return roundValue;
    }

    public static double changeSign(double value)
    {
        double oppositeValue=-1*value;
        return oppositeValue;
    }

    public static String decimalTwoDigit(double value)
    {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String troDecimalValue=decimalFormat.format(value);
        return troDecimalValue;
    }

}
