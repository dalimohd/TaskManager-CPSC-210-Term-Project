package ui;

import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class PrintManager {
    Parser parser = new Parser();

    public PrintManager(){

    }

    public void print(String s){
        System.out.println(s);
    }

    // REQUIRES: list with at least one task
    // EFFECTS: reads out and indexes each task in a TaskList
    public void displayActiveTasks(TaskList tl){
            if (tl.getTaskListSize() > 0) {
                int count = 1;
                for (Task t : tl.getCurrentTasks()) {
                    if (t instanceof DueTask) {
                        System.out.println(count + ": '" + t.getName() + "' at " + t.getLocationName() + "  - - -  due by: " + t.getDate() + ". Status: " + t.onTime());
                    } else if (t instanceof SimpleTask) {
                        System.out.println(count + ": '" + t.getName() + "' at " + t.getLocationName());
                    }
                    count++;
                }
            } else {
                System.out.println("There are no items to display");
            }
            System.out.println("\n");
    }

    public void displayLocationMap(TaskList taskList) throws IOException {
        Map locationMap = taskList.getLocationMap().getMap();

        Iterator it = locationMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Task> displayedLocTaskList = (ArrayList<Task>)pair.getValue();
            String keyName = (String)pair.getKey();
            String keyWeather = parser.getWeather(keyName);

            print(keyName+":");
            if(!(keyWeather == null)) {print("Current weather in " + keyName + ": " + keyWeather);}

            if (displayedLocTaskList.size() > 0) {
                int count = 1;
                for (Task t : displayedLocTaskList) {
                    if (t instanceof DueTask) {
                        print(count + ": '" + t.getName() + "  - - -  due by: " + t.getDate() + ". Status: " + t.onTime());
                    } else if (t instanceof SimpleTask) {
                        print(count + ": '" + t.getName());
                    }
                    count++;
                }
            } else {
                print("There are no items to display");
            }
            print("\n");
        }
        //src: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
    }

    public ArrayList<String> convertLocationMapAsStringList(TaskList taskList) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        Map locationMap = taskList.getLocationMap().getMap();

        Iterator it = locationMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Task> displayedLocTaskList = (ArrayList<Task>) pair.getValue();
            String keyName = (String) pair.getKey();
            String keyWeather = parser.getWeather(keyName);

            list.add(keyName);
            if (!(keyWeather == null)) {
                list.add("Current weather in " + keyName + ": " + keyWeather);
            }

            if (displayedLocTaskList.size() > 0) {
                int count = 1;
                for (Task t : displayedLocTaskList) {
                    if (t instanceof DueTask) {
                        list.add(count + ": '" + t.getName() + "  - - -  due by: " + t.getDate() + ". Status: " + t.onTime());
                    } else if (t instanceof SimpleTask) {
                        list.add(count + ": '" + t.getName());
                    }
                    count++;
                }
                list.add(" ");
            } else {
                list.add("There are no items to display");
            }
            //src: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
        }

        return list;
    }



    // REQUIRES: list of String
    // EFFECTS: prints all strings in list
    public void printStringList(ArrayList<String> l) {
        for (String s : l) {
            System.out.println("> " + s);
        }
        System.out.println("\n");
    }
}

