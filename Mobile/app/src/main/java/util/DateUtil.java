package util;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date GetDate(int ano, int mes, int dia){
        Calendar cl = Calendar.getInstance();
        cl.set(ano,mes,dia);
        return cl.getTime();
    }

    public static String dateToString(int ano, int mes, int dia){
        Calendar cl = Calendar.getInstance();
        cl.set(ano,mes,dia);
        Date dt = cl.getTime();
        DateFormat format=DateFormat.getDateInstance(DateFormat.SHORT);
        return format.format(dt);
    }

    public static String dateToString(Date date){
        if(date!=null) {
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
            return format.format(date);
        }else
            return "";
    }


    public static  Date GetDate(String Date){
        int dia = Integer.parseInt(Date.split("/")[0]);
        int mes = Integer.parseInt(Date.split("/")[1]);
        int ano = Integer.parseInt(Date.split("/")[1]);
        return GetDate(ano,mes,dia);
    }
    ////
    //retorna a data atual
    public static  Date GetDate(){
        Calendar cl = Calendar.getInstance();
        return  cl.getTime();
    }
}
