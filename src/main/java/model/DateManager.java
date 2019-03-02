package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager implements Serializable {
    Date currentDateTime = new Date(); // new dates default to the current date & time
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    Date date;

    DateManager(){
    }

    public void setDate(String date) throws ParseException {
        this.date = dateFormat.parse(date);
    }

    public String getDate(){
        return this.date.toString();
    }


    // REQUIRES: this
    // EFFECTS: tells if the task is on time or overdue
    public String onTime(){
        String onTime = "";
        if (date.after(currentDateTime)){
            onTime = "on time";
        }
        else if (date.before(currentDateTime)){
            onTime = "overdue";
        }
        return(onTime);
    }
}
