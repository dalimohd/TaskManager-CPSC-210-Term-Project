package model;

import java.text.ParseException;
import java.util.Date;

public class DueTask extends Task {

    public void setDate(String dueDate) throws ParseException {
        dm.setDate(dueDate);
    }

    public String getDate(){
        return dm.getDate();
    }


    // REQUIRES: this
    // EFFECTS: tells if the task is on time or overdue
    public String onTime(){
        return dm.onTime();
    }
}
