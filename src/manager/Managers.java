package manager;

import java.io.File;
import java.io.IOException;

public class Managers {

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static TaskManager getDefault() {
        try {
            File file = new File("C:\\Users\\xatop\\dev\\java-kanban\\java-kanban\\src\\memory\\data.csv");
            return FileBackedTasksManager.loadFromFile(file);
        } catch (ManagerSaveException | IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
