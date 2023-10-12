package com.example.gestionepatrimonio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.example.gestionepatrimonio.entities.Budget;
import com.example.gestionepatrimonio.entities.MovimentType;
import com.example.gestionepatrimonio.entities.Moviments;
import com.example.gestionepatrimonio.services.MathOperation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DbManagerMoviments {

    private DbHelperMoviments dbHelperMoviments;
    private final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd", Locale.ITALIAN);

    public DbManagerMoviments(Context context)
    {
        this.dbHelperMoviments=new DbHelperMoviments(context);
    }

    //recupera tutti i tipi di movimento
    public HashMap<Integer,MovimentType> getMovimentType()
    {
        Cursor crs;
        HashMap<Integer,MovimentType> movimentTypeList=new HashMap<Integer,MovimentType>();
        try
        {
            SQLiteDatabase db=dbHelperMoviments.getReadableDatabase();
            String[] strings=DbStrings.movimentsType;
            crs=db.query(strings[0], null, null, null, null, null, strings[2], null);
            for(int i=0;i<crs.getCount();i++)
            {
                crs.moveToNext();
                int idMovimentType=crs.getInt(0);
                String movimentTypeName=crs.getString(1);
                double maxValue=crs.getDouble(2);
                MovimentType movimentType=new MovimentType(idMovimentType,movimentTypeName,maxValue);
                movimentTypeList.put(idMovimentType,movimentType);
            }
        }
        catch(Exception e)
        {
            System.out.println("Errore in DbManagerMoviments.getMovimentType");
            e.printStackTrace();
            return null;
        }
        return movimentTypeList;
    }

    //recupera tipologia movimento da ID
    public MovimentType getMovimentById(int idMovimentType)
    {
        Cursor crs;
        try
        {
            SQLiteDatabase db=dbHelperMoviments.getReadableDatabase();
            String[] strings=DbStrings.movimentsType;
            crs=db.query(strings[0], null, null, null, null, null, strings[1], null);
            for(int i=0;i<crs.getCount();i++)
            {
                crs.moveToNext();
                int idMovimentTypeCsr=Integer.parseInt(crs.getString(0));
                if(idMovimentTypeCsr==idMovimentType)
                {
                    String movimentTypeName= crs.getString(1);
                    double maxValue=Double.parseDouble(crs.getString(2));
                    MovimentType movimentType=new MovimentType(idMovimentTypeCsr,movimentTypeName,maxValue);
                    return movimentType;
                }
            }
            return null;
        }
        catch(SQLiteException sqle)
        {
            System.out.println("Errore DbManagerMoviments.getMovimentById");
            sqle.printStackTrace();
            return null;
        }
    }

    //Aggiungi movimento
    public boolean SaveMoviment(@NonNull Moviments moviments)
    {
        SQLiteDatabase db=dbHelperMoviments.getWritableDatabase();
        ContentValues cv=new ContentValues();
        String[] strings=DbStrings.moviments;
        cv.put(strings[2],moviments.getMovimentDateString());
        cv.put(strings[3],moviments.getValue());
        cv.put(strings[4],moviments.getIdMovimentType());
        try
        {
            db.insert(strings[0], null,cv);
            return true;
        }
        catch (SQLiteException sqle)
        {
            sqle.printStackTrace();
            return false;
        }
    }


    //recupera tutti i movimenti
    public HashMap<Integer,Moviments> getMoviments(int order)
    {
        Cursor crs;
        HashMap<Integer,Moviments> movimentsList=new HashMap<Integer,Moviments>();
        try
        {
            SQLiteDatabase db=dbHelperMoviments.getReadableDatabase();
            String[] strings=DbStrings.moviments;
            crs=db.query(strings[0], null, null, null, null, null, strings[order], null);
            for(int i=0;i<crs.getCount();i++)
            {
                crs.moveToNext();
                int idMoviment=crs.getInt(0);
                Date movimentDate=simpleDateFormat.parse(crs.getString(1));
                int idMovimentType=crs.getInt(3);
                double value=crs.getDouble(2);
                Moviments movimento=new Moviments(idMoviment,movimentDate,idMovimentType,value);
                movimentsList.put(idMoviment,movimento);
            }
        }
        catch(SQLiteException sqle)
        {
            sqle.printStackTrace();
            return null;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            return null;
        }
        return movimentsList;
    }

    //recupera totale movimenti
    public double getTotalValue()
    {
        Cursor crs;
        double totalValue=0;
        try
        {
            SQLiteDatabase db=dbHelperMoviments.getReadableDatabase();
            String[] strings=DbStrings.moviments;
            crs=db.query(strings[0], null, null, null, null, null, strings[2], null);
            for(int i=0;i<crs.getCount();i++)
            {
                crs.moveToNext();
                double value=crs.getDouble(2);
                totalValue=totalValue+value;
            }
        }
        catch(SQLiteException sqle)
        {
            sqle.printStackTrace();
            return 0;
        }
        return totalValue;
    }

    //recupera totale movimenti per tipologia
    private double totalByType(int idMovimentType) {
        Cursor crs;
        double totalValue=0;
        try
        {
            SQLiteDatabase db=dbHelperMoviments.getReadableDatabase();
            String[] strings=DbStrings.moviments;
            crs=db.query(strings[0], null, null, null, null, null, strings[2], null);
            for(int i=0;i<crs.getCount();i++)
            {
                crs.moveToNext();
                if(crs.getInt(3)==idMovimentType)
                {
                    double value=crs.getDouble(2);
                    totalValue=totalValue+value;
                }
            }
        }
        catch(SQLiteException sqle)
        {
            System.out.println("Errore in DbManagerMoviments.totalByType");
            sqle.printStackTrace();
            return 0;
        }
        catch(Exception e)
        {
            System.out.println("Errore in DbManagerMoviments.totalByType");
            e.printStackTrace();
            return 0;
        }
        return totalValue;
    }


    public List<Budget> getBudgets()
    {
        try
        {
            List<Budget> budgetList=new ArrayList<Budget>();
            HashMap<Integer,MovimentType> movimentTypeList=getMovimentType();
            for (Integer movimentId:movimentTypeList.keySet()) {
                double actualValue=totalByType(movimentTypeList.get(movimentId).getIdMovimentType());
                Budget budget=new Budget(movimentTypeList.get(movimentId),actualValue);
                budgetList.add(budget);
            }
            return budgetList;
        }catch(Exception e)
        {
            System.out.println("Errore in DbManagerMoviments.getBudgets");
            e.printStackTrace();
            return null;
        }
    }


    public String getTotalBudget(List<Budget> budgetList) {
        try
        {
            String budgetString;
            double AcutalValue=0;
            double TotalValue=0;
            for (Budget budget:budgetList) {
                AcutalValue=AcutalValue+budget.getActualValue();
                TotalValue=TotalValue+budget.getMovimentType().getMaxValue();
            }
            String ActualValueString=MathOperation.decimalTwoDigit(MathOperation.roundTwo(MathOperation.changeSign(AcutalValue)));
            String TotalValueString=MathOperation.decimalTwoDigit(MathOperation.roundTwo(TotalValue));
            budgetString=ActualValueString+"/"+TotalValueString;
            return budgetString;
        }catch(Exception e)
        {
            System.out.println("Errore in DbManagerMoviments.getTotalBudget");
            e.printStackTrace();
            return null;
        }
    }
}
