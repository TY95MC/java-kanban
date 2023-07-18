package managerTest;

import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private final HistoryManager manager = new InMemoryHistoryManager();

    private void addSomeTasks() {
        manager.add(new Epic("epic", "description", 1));
        manager.add(new Subtask("subtask", "description",Status.NEW, 2, 1));
        manager.add(new Subtask("subtask", "description",Status.NEW, 3, 1));
        manager.add(new Task("task", "description", Status.NEW, 4));
    }

    @Test
    void addWhenExist() {
        addSomeTasks();
        manager.add(new Subtask("subtask", "description",Status.NEW, 3, 1));
        assertEquals(4, manager.getHistory().size());
    }

    @Test
    void addWhenEmpty() {
        manager.add(new Task("task", "description", Status.NEW, 1));
        assertEquals(1, manager.getHistory().size());
    }

    @Test
    void removeWhenEmptyHistory() {
        manager.remove(150);
        assertTrue(manager.getHistory().isEmpty());
    }

    @Test
    void removeHeadFromHistory() {
        addSomeTasks();
        manager.remove(1);
        List<Task> list= List.of(
                new Subtask("subtask", "description",Status.NEW, 2, 1),
                new Subtask("subtask", "description",Status.NEW, 3, 1),
                new Task("task", "description", Status.NEW, 4));
        assertEquals(list, manager.getHistory());
    }

    @Test
    void removeTailFromHistory() {
        addSomeTasks();
        manager.remove(3);
        List<Task> list= List.of(
                new Epic("epic", "description", 1),
                new Subtask("subtask", "description",Status.NEW, 2, 1),
                new Task("task", "description", Status.NEW, 4));
        assertEquals(list, manager.getHistory());
    }

    @Test
    void removeMiddleElementFromHistory() {
        addSomeTasks();
        manager.remove(4);
        List<Task> list= List.of(
                new Epic("epic", "description", 1),
                new Subtask("subtask", "description",Status.NEW, 2, 1),
                new Subtask("subtask", "description",Status.NEW, 3, 1));
        assertEquals(list, manager.getHistory());
    }

}