package managerTest;

import manager.ManagerValidateException;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {

     protected T manager;

    @AfterEach
    public void afterEach(){
        manager.deleteTasks();
        manager.deleteEpics();
        manager.deleteSubtasks();
    }

    @Test
    void getTasks() {
        manager.addTask(new Task("task", "description"));
        manager.addTask(new Task("task", "description"));
        assertEquals(2, manager.getTasks().size());
    }

    @Test
    void getTasksWhenHashMapIsEmpty() {
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void getSubtasks() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        assertEquals(2, manager.getSubtasks().size());
    }

    @Test
    void getSubtasksWhenHashMapIsEmpty() {
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void getEpics() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addEpic(new Epic("epic1", "description"));
        manager.addEpic(new Epic("epic2", "description"));
        assertEquals(3, manager.getEpics().size());
    }

    @Test
    void getEpicsWhenHashMapIsEmpty() {
        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void deleteTasks() {
        manager.addTask(new Task("task", "description"));
        manager.addTask(new Task("task", "description"));
        manager.deleteTasks();
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void deleteEpics() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addEpic(new Epic("epic", "description"));
        manager.deleteEpics();
        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void deleteSubtasks() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        manager.deleteSubtasks();
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void getTask() {
        manager.addTask(new Task("task", "description", Status.NEW));
        Task task = new Task("task", "description", Status.NEW, 1);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void getTaskWithWrongId() {
        final ManagerValidateException exception = assertThrows(
                ManagerValidateException.class,
                () -> manager.getTask(200));
        assertEquals("Вы ввели неверный идентификатор!", exception.getMessage());
    }

    @Test
    void getEpic() {
        manager.addEpic(new Epic("epic", "description"));
        Epic epic = new Epic("epic", "description", Status.NEW, 1);
        assertEquals(epic, manager.getEpic(1));
    }

    @Test
    void getEpicWithWrongId() {
        final ManagerValidateException exception = assertThrows(
            ManagerValidateException.class,
                () -> manager.getEpic(200));
        assertEquals("Вы ввели неверный идентификатор!", exception.getMessage());
    }

    @Test
    void getSubtask() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        Subtask subtask = new Subtask("subtask", "description", Status.NEW, 2, 1);
        assertEquals(subtask, manager.getSubtask(2));
    }

    @Test
    void getSubtaskWithWrongId() {
        final ManagerValidateException exception = assertThrows(
                ManagerValidateException.class,
                () -> manager.getSubtask(200));
        assertEquals("Вы ввели неверный идентификатор!", exception.getMessage());
    }

    @Test
    void checkIfEpicIsAdded() {
        manager.addEpic(new Epic("epic", "description"));
        Epic epic = new Epic("epic", "description", Status.NEW, 1);
        assertEquals(epic, manager.getEpic(1));
    }

    @Test
    void checkIfTaskIsAdded() {
        manager.addTask(new Task("task", "description", Status.NEW));
        Task task = new Task("task", "description", Status.NEW, 1);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void checkIfSubtaskIsAdded() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        Subtask subtask = new Subtask("subtask", "description", Status.NEW, 2, 1);
        assertEquals(subtask, manager.getSubtask(2));
    }

    @Test
    void checkIfTaskIsUpdated() {
        manager.addTask(new Task("task", "description", Status.NEW));
        Task task = new Task("task", "description", Status.NEW, 1);
        Task task1 = manager.getTask(1);
        task1.setDescription("update");
        manager.updateTask(task1);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void checkIfEpicIsUpdated() {
        manager.addEpic(new Epic("epic", "description"));
        Epic epic = new Epic("epic", "description", 1);
        Epic epic1 = manager.getEpic(1);
        epic1.setDescription("update");
        manager.updateEpic(epic1);
        assertEquals(epic, manager.getEpic(1));
    }

    @Test
    void checkIfSubtaskIsUpdated() {
        Subtask subtask = new Subtask("subtask", "description", Status.NEW, 2, 1);
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        Subtask subtask1 = manager.getSubtask(2);
        subtask1.setDescription("update");
        manager.updateSubtask(subtask1);
        assertEquals(subtask, manager.getSubtask(2));
    }

    @Test
    void checkIfTaskIsDeleted() {
        manager.addTask(new Task("task", "description"));
        manager.deleteTask(1);
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    void checkIfEpicIsDeleted() {
        manager.addEpic(new Epic("epic", "description"));
        manager.deleteEpic(1);
        assertTrue(manager.getEpics().isEmpty());
    }

    @Test
    void checkIfSubtaskIsDeleted() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        manager.deleteSubtask(2);
        assertTrue(manager.getSubtasks().isEmpty());
    }

    @Test
    void getListOfSubtasksOfItsEpic() {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", Status.NEW, 1));
        assertEquals(1, manager.getEpic(1).getSubtaskIds().size());
    }

    @Test
    void getPrioritizedTasks() {
        Task task = new Task("first", "firstTask");
        task.setStartTime(LocalDateTime.now().minus(Duration.ofMinutes(30)));
        task.setDuration(Duration.ofMinutes(5));
        manager.addTask(task);
        assertEquals(1, manager.getPrioritizedTasks().size());
    }

    @Test
    void checkEpicStatusWhenNoSubtasks() {
        manager.addEpic(new Epic("epic", "description"));
        assertEquals(manager.getEpic(1).getStatus(), Status.NEW);
    }

    private void createEpicAndSubtask(Status status1, Status status2) {
        manager.addEpic(new Epic("epic", "description"));
        manager.addSubtask(new Subtask("subtask", "description", status1, 1));
        manager.addSubtask(new Subtask("subtask", "description", status2, 1));
    }

    @Test
    void checkEpicStatusWhenAllSubtasksHasStatusNew() {
        createEpicAndSubtask(Status.NEW, Status.NEW);
        assertEquals(manager.getEpic(1).getStatus(), Status.NEW);
    }

    @Test
    void checkEpicStatusWhenAllSubtasksHasStatusDone() {
        createEpicAndSubtask(Status.DONE, Status.DONE);
        assertEquals(manager.getEpic(1).getStatus(), Status.DONE);
    }

    @Test
    void checkEpicStatusWhenAllSubtasksHasStatusNewAndDone() {
        createEpicAndSubtask(Status.NEW, Status.DONE);
        assertEquals(manager.getEpic(1).getStatus(), Status.IN_PROGRESS);
    }

    @Test
    void checkEpicStatusWhenAllSubtasksHasStatusInProgress() {
        createEpicAndSubtask(Status.IN_PROGRESS, Status.IN_PROGRESS);
        assertEquals(manager.getEpic(1).getStatus(), Status.IN_PROGRESS);
    }
}