package model;

import java.io.Serializable;

public class Location implements Serializable {
    String locationName;

    public Location(){
    }

    public void setLocationName(String name){
        locationName = name;
    }

    public String getLocationName(){
        return locationName;
    }

}