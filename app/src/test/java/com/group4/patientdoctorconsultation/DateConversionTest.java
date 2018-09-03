package com.group4.patientdoctorconsultation;

import com.group4.patientdoctorconsultation.utilities.BindingAdapters;

import org.junit.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DateConversionTest {

    private Date currentDate = Calendar.getInstance().getTime();

    @Test
    public void dateToSimpleDateString() {
        assertEquals(
                BindingAdapters.dateFormat.format(currentDate),
                BindingAdapters.getSimpleDateString(currentDate)
        );
    }

    @Test
    public void simpleStringToDate(){
        try {
            assertEquals(
                    BindingAdapters.dateFormat.parse(BindingAdapters.dateFormat.format(currentDate)),
                    BindingAdapters.getDateFromSimpleString(BindingAdapters.dateFormat.format(currentDate))
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}