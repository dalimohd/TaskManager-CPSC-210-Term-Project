package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Task implements Serializable{
    DateManager dm = new DateManager();
    String taskName;
    Location location = new Location();
    boolean isActive;

    public Task() {
    }

    public String getName(){
        return this.taskName;
    }

    public void setName(String name){
        this.taskName = name;
    }


    public void setActive(boolean active){
        this.isActive = active;
    }

    public abstract String getDate();

    public abstract void setDate(String dueDate) throws ParseException;

    public void setLocation(String name){
        location.setLocationName(name);
    }

    public String getLocationName(){
        return location.getLocationName();
}

    public Boolean getStatus(){
        return this.isActive;
    }

    // REQUIRES: this
    // EFFECTS: tells if the task is on time or overdue
    public abstract String onTime();

}
