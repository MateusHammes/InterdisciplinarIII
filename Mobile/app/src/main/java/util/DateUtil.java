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
        DateFormat format=DateFormat.getDateInstance(DateFormat.SHORT);
        return format.format(date);
    }


}
