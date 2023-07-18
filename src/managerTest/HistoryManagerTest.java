package managerTest;

import manager.FileBackedTaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Task;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private FileBackedTaskManager manager;
    private final File testFile = new File("java-kanban/src/memory/dataTest.csv");

    @BeforeEach
    void createManager() {
        manager = new FileBackedTaskManager(testFile);
    }

    @Test
    void getEmptyHistory() {
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    void checkNoCopiesInHistory() {
        manager.addEpic(new Epic("epic", "description"));
        manager.getEpic(1);
        manager.getEpic(1);
        manager.getEpic(1);
        assertEquals(1, manager.getHistory().size());
    }

    private void createEpicANdTasks() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addTask(new Task("task", "description"));
        manager.addTask(new Task("task", "description"));
        manager.getEpic(1);
        manager.getTask(2);
        manager.getTask(3);
    }

    @Test
    void deleteHeadFromHistory() {
        Task task = new Task("task", "description", 2);
        Task task1 = new Task("task", "description", 3);
        createEpicANdTasks();
        manager.deleteEpic(1);
        List<Task> list= List.of(task, task1);
        assertEquals(list, manager.getHistory());
    }

    @Test
    void deleteTailFromHistory() {
        Epic epic = new Epic("epic", "description", 1);
        Task task = new Task("task", "description", 2);
        createEpicANdTasks();
        manager.deleteTask(3);
        List<Task> list= List.of(epic, task);
        assertEquals(list, manager.getHistory());
    }

    @Test
    void deleteMiddleElementFromHistory() {
        Epic epic = new Epic("epic", "description", 1);
        Task task = new Task("task", "description", 3);
        createEpicANdTasks();
        manager.deleteTask(2);
        List<Task> list= List.of(epic, task);
        assertEquals(list, manager.getHistory());
    }
}