package ui;

import model.TaskList;

import java.io.*;

public class FileManager {

    public FileManager(){
    }

    // EFFECTS: loads the saved TaskList
    public TaskList load(String name) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(name));
        TaskList taskList = (TaskList) objectInputStream.readObject();
        objectInputStream.close();
        return taskList;
    }

    // REQUIRES: A TaskList
    // EFFECTS: saves the TaskList to a file
    public void save(String name, TaskList tl) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(name));
        objectOutputStream.writeObject(tl);
        objectOutputStream.close();
    }
}
