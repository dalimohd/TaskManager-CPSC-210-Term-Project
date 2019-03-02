package model;

import java.io.Serializable;
import java.util.*;

public class LocationMap implements Serializable {
    Map<String, ArrayList<Task>> locationMap = new HashMap<>();

    public LocationMap() {
    }

    public Map getMap(){
        return locationMap;
    }

    public void addToMap(String key, Task task) {
        ArrayList<Task> locationTasks = locationMap.get(key);

        // if list does not exist create it
        if (locationTasks == null) {
            locationTasks = new ArrayList<Task>();
            locationTasks.add(task);
            locationMap.put(key, locationTasks);
        } else {
            // add if task is not already in list corresponding to location
            if (!locationTasks.contains(task)) {
                locationTasks.add(task);
                task.setLocation(key);
            }
        }
        //src: https://www.quora.com/How-do-you-add-an-element-to-an-arraylist-thats-in-a-hashmap
    }

    public void removeFromMap(String key, Task task) {
        ArrayList<Task> locationTasks = locationMap.get(key);

        // if list does not exist create it
        if (locationTasks != null) {
            locationTasks.remove(task);
            if (locationTasks.isEmpty()){
                locationMap.remove(key);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocationMap)) return false;
        LocationMap that = (LocationMap) o;
        return Objects.equals(locationMap, that.locationMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationMap);
    }
}
