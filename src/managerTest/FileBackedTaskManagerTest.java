package managerTest;

import manager.FileBackedTaskManager;
import manager.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class FileBackedTaskManagerTest extends TaskManagerTest {

    File file = new File("C:/Users/xatop/dev/java-kanban/java-kanban/src/memory/data.csv");
    File testFile = new File("C:/Users/xatop/dev/java-kanban/java-kanban/src/memory/dataTest.csv");

    @BeforeEach
    void createManager() throws IOException {
        manager = FileBackedTaskManager.loadFromFile(file);
    }

    @Test
    void loadFromFileWhenHistoryIsEmpty() throws IOException {
        try (Writer fw = new FileWriter(testFile.getPath(), StandardCharsets.UTF_8)) {
            List<String> list = new ArrayList<>();
            final String COLUMNS = "id,type,name,status,description,startTime,duration,endTime,epic";
            list.add(COLUMNS);
            list.add("1,TASK,first,NEW,firstTask,null,null,null");
            list.add("2,EPIC,first,NEW,firstEpicTask,null,null,null");
            list.add("3,SUBTASK,first,NEW,firstEpicTask,null,null,null,2");
            list.add("");
            for (String line : list) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Записи не произошло!");
        }
        manager = FileBackedTaskManager.loadFromFile(testFile);
        assertTrue(manager.getTasks().size() == 1 &&
                manager.getEpics().size() == 1 && manager.getHistory().size() == 0);
    }

    @Test
    void loadFromFileWhenTasksListIsEmpty() throws IOException {
        try (Writer fw = new FileWriter(testFile.getPath(), StandardCharsets.UTF_8)) {
            List<String> list = new ArrayList<>();
            final String COLUMNS = "id,type,name,status,description,startTime,duration,endTime,epic";
            list.add(COLUMNS);
            list.add("1,EPIC,first,NEW,firstEpicTask,null,null,null");
            list.add("2,SUBTASK,first,NEW,firstEpicTask,null,null,null,1");
            list.add("");
            for (String line : list) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Записи не произошло!");
        }
        manager = FileBackedTaskManager.loadFromFile(testFile);
        assertTrue(manager.getSubtasks().size() == 1 &&
                manager.getEpics().size() == 1 && manager.getHistory().size() == 0);
    }

    @Test
    void loadFromFileWhenEpicHasNoSubtasks() throws IOException {
        try (Writer fw = new FileWriter(testFile.getPath(), StandardCharsets.UTF_8)) {
            List<String> list = new ArrayList<>();
            final String COLUMNS = "id,type,name,status,description,startTime,duration,endTime,epic";
            list.add(COLUMNS);
            list.add("1,TASK,first,NEW,firstTask,null,null,null");
            list.add("2,EPIC,first,NEW,firstEpicTask,null,null,null");
            list.add("");
            list.add("1");
            for (String line : list) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Записи не произошло!");
        }
        manager = FileBackedTaskManager.loadFromFile(testFile);
        assertTrue(manager.getTasks().size() == 1 &&
                manager.getEpics().size() == 1 && manager.getHistory().size() == 1 && manager.getTasks().size() == 1 &&
                manager.getEpic(2).getSubtaskIds().size() == 0);
    }
}