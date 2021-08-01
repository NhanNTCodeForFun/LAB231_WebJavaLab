/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhannt.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class DateCaculator implements Serializable {

    public long diffDate(String begin, String end) throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;
        long getDaysDiff;

        date1 = simpleDateFormat.parse(begin);
        date2 = simpleDateFormat.parse(end);
        long getDiff = date2.getTime() - date1.getTime();
        getDaysDiff = getDiff / (24 * 60 * 60 * 1000);
        return getDaysDiff;
    }
}
