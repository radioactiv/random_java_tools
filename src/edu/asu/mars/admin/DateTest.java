package edu.asu.mars.admin;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class DateTest {

    public DateTest() {
        Calendar startCalendar=  new GregorianCalendar();
        startCalendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        startCalendar.clear();
        startCalendar.set(2001, Calendar.JANUARY, 1);
        Date startDate = startCalendar.getTime();
        Calendar todayCalendar = new GregorianCalendar();
//        Calendar todayCalendarSimple = new GregorianCalendar(todayCalendar.get(Calendar.YEAR), todayCalendar.get(Calendar.MONTH), todayCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar todayCalendarSimple = new GregorianCalendar(); // 2 years -- No leap year in this interval
        todayCalendarSimple.clear();
        todayCalendarSimple.setTimeZone(TimeZone.getTimeZone("GMT"));
        todayCalendarSimple.set(2012, Calendar.JANUARY, 1); // 2 years -- No leap year in this interval // now at 11 there are some
        System.out.println("Start Timezone is: " + startCalendar.getTimeZone().getRawOffset());
        System.out.println("End Timezone is: " + todayCalendarSimple.getTimeZone().getRawOffset());
        //Difference in seconds is 63072000 (in milli 6307200000)
        Date todaySimple = todayCalendarSimple.getTime();
        System.out.println("Start Date Epoch " + startDate.getTime());
        System.out.println("End Date Epoch   " + todaySimple.getTime());
//        long difference =  todaySimple.getTime() - startDate.getTime();
        long difference =  todayCalendarSimple.getTimeInMillis() - startCalendar.getTimeInMillis();
        System.out.println("Difference       " + difference);
        long dayDifference = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        System.out.println("Difference converted to days: " + dayDifference);
//        long difference2 = todayCalendarSimple.getTimeInMillis() - startCalendar.getTimeInMillis();


        System.out.println("# of days in interval "+dayDifference);// I think it should be 730 days, not 729... 365 * 2 = 730
        long secondDifference = dayDifference * 24 *60 * 60 *1000;
        difference =  todayCalendarSimple.getTimeInMillis() - startCalendar.getTimeInMillis();
//        System.out.println(difference);
//        System.out.println(difference2);
        System.out.println("Difference in milliseconds "+secondDifference); // Correct
        System.out.println("Calc difference minus simple difference "+(difference - secondDifference));


    }
}
