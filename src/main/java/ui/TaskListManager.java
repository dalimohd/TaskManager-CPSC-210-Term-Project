package ui;

import exceptions.TooManyItemsException;
import model.DueTask;
import model.SimpleTask;
import model.Task;
import model.TaskList;

import java.io.*;
import java.text.ParseException;
import java.util.*;

public class TaskListManager implements Serializable, Observer {
    TaskList opEntry = new TaskList(this);
    PrintManager pm = new PrintManager();
    FileManager fm = new FileManager();
    Scanner scanner = new Scanner(System.in);

    public TaskListManager() throws ParseException, IOException, ClassNotFoundException, TooManyItemsException {

        String operation = "";
        ArrayList<String> validOperations = new ArrayList<>();
        validOperations.add("add"); validOperations.add("remove"); validOperations.add("view"); validOperations.add("location view (lv)"); validOperations.add("save"); validOperations.add("load"); validOperations.add("quit");

        while (true) {
            pm.print("Please select an option:");
            pm.printStringList(validOperations);
            operation = scanner.nextLine();
            if (validOperations.contains(operation)){
                pm.print("You selected " + operation + ":");
            }

            if (operation.equals("add")) {
                try {
                    add();
                }catch (TooManyItemsException e){
                    pm.print(e.getMessage());
                }finally{
                    assert (opEntry.getTaskListSize() <= opEntry.MAX_ITEMS_IN_LIST);
                }
            } else if (operation.equals("remove")) {
                remove();
            } else if (operation.equals("view")) {
                    view();
            } else if (operation.equals("lv") || operation.equals("location view")){
                locView();
            } else if (operation.equals("save")){
                save();
            }
            else if (operation.equals("load")){
                load();
            }
            else if (operation.equals("quit")) {
                break;
            }
        }
    }

    // REQUIRES: TaskList
    // MODIFIES: TaskList
    // EFFECTS: provides an interface to add new tasks to a list, only if there is < 15 tasks
    public void add() throws ParseException, TooManyItemsException{

        Task task;
        String taskDueDateString = new String();

        pm.print("What would you like to do?");
        String taskName = scanner.nextLine();

        pm.print("Would you like to have a due date added to this item? (y/n)");
        String wantsDueDate = scanner.nextLine();
        if (wantsDueDate.equals("y")){
            task = new DueTask();
            while (true) {
                try {
                    pm.print("Date and time you would like to complete it by? [Format: yyyy-MM-dd hh:mm]");
                    taskDueDateString = scanner.nextLine();
                    task.setDate(taskDueDateString);
                    break;
                } catch (ParseException e) {
                    pm.print("Invalid date format! Try again");
                }
            }
        } else {
            task = new SimpleTask();
        }


        pm.print("Location this task is to be completed?");
        String location = scanner.nextLine();


        task.setName(taskName);
        task.setLocation(location);
        task.setActive(true);
        opEntry.addTask(task);

    }


    // REQUIRES: TaskList
    // MODIFIES: TaskList
    // EFFECTS: provides an interface to display all the tasks in a list and remove them
    public void remove() {
        pm.displayActiveTasks(opEntry);
        if (opEntry.getTaskListSize() > 0) {
            pm.print("Which of the above items have you completed?" + "\n" + "type any letter if you no longer want to remove an item");
            try {
                int itemIndex = scanner.nextInt();
                opEntry.removeTask(itemIndex);
            } catch (InputMismatchException i) {
            }
        }
    }

    public void save() throws IOException {
        try {
            fm.save("saveState", opEntry);
            pm.print("Task Manager State Saved"+ "\n");
        } catch (IOException e){
            pm.print("Unable to save state!");
        }

    }

    public void load() throws IOException, ClassNotFoundException {
        try {
            opEntry = fm.load("saveState");
            pm.print("Saved Task Manager Loaded"+ "\n");
        } catch (IOException | ClassNotFoundException e){
            pm.print("Unable to load state!");

        }

    }


    public void view() {
    pm.displayActiveTasks(opEntry);
    }

    public void locView() throws IOException {pm.displayLocationMap(opEntry);}


    @Override
    public void update(Observable o, Object arg) {
        TaskList observedTaskList = (TaskList) o;
        Task observedTask = (Task) arg;
        pm.print("\n");

        if (!observedTaskList.getJustAdded()){pm.print("Task: '" + observedTask.getName() + "' has been removed");}
        if (observedTaskList.getJustAdded()){pm.print("Task: '" + observedTask.getName() + "' has been added to your active tasks");}

        pm.print("There are now "+observedTaskList.getTaskListSize()+" tasks in your active tasks list"+ "\n");

    }
}
