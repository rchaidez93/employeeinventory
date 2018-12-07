package com.employees.employeeinventory.Utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValidator {

    final static String dateFormat = "dd/mm/yyyy";

    public boolean validateDate(String date){

        if(date.trim().equals("")){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);

        try{
            sdf.parse(date);
        }catch(ParseException ex){

            return false;
        }

        return true;
    }
}
