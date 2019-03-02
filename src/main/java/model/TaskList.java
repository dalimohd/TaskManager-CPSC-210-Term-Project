package model;

import exceptions.TooManyItemsException;
import ui.TaskListManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class TaskList extends Observable implements Serializable {
    public final int MAX_ITEMS_IN_LIST = 15;
    String operation = "";
    private ArrayList<Task> currentTasks = new ArrayList<>();
    private ArrayList<Task> completedTasks = new ArrayList<>();
    private LocationMap lm = new LocationMap();
    Boolean justAdded;

    public TaskList(TaskListManager tlm){
        addObserver(tlm);
    }
    public TaskList(){
    }

    public boolean getJustAdded(){
        return justAdded;
    }

    // REQUIRES: a task
    // MODIFIES: this
    // EFFECTS: adds the tasks to a list
    public void addTask(Task task) throws TooManyItemsException{
        lm.addToMap(task.getLocationName(),task);
        currentTasks.add(task);
        justAdded = true;
        setChanged();
        notifyObservers(task);
    }

    public ArrayList<Task> getCurrentTasks(){
        return currentTasks;
    }

    // REQUIRES: list with at least one task, and a valid index number
    // MODIFIES: this
    // EFFECTS: removes the task from the list
    public void removeTask(int index) {
        Task selectedTask = currentTasks.get(index - 1);
        lm.removeFromMap(selectedTask.getLocationName(), selectedTask);
        completedTasks.add(selectedTask);
        currentTasks.remove(selectedTask);
        justAdded = false;
        setChanged();
        notifyObservers(selectedTask);
    }

    public LocationMap getLocationMap(){
        return lm;
    }


    // REQUIRES: list with at least one task
    // EFFECTS: reads out and indexes each task in a TaskList
    public ArrayList<String> convertTaskListToStringView() {
        ArrayList<String> list = new ArrayList<>();
        if (this.getTaskListSize() > 0) {
            int count = 1;
            for (Task t : this.getCurrentTasks()) {
                if (t instanceof DueTask) {
                    list.add(count + ": '" + t.getName() + "' at " + t.getLocationName() + "  - - -  due by: " + t.getDate() + ". Status: " + t.onTime());
                } else if (t instanceof SimpleTask) {
                    list.add(count + ": '" + t.getName() + "' at " + t.getLocationName());
                }
                count++;
            }
        } else {
            list.add("There are no items to display");
        }
        return list;
    }


    // EFFECTS: returns size of active items in TaskList
    public int getTaskListSize(){
        return(currentTasks.size());
    }

}