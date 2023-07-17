package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.Collection;
import java.util.List;

public interface TaskManager {

    Collection<Task> getTasks();
    Collection<Subtask> getSubtasks();
    Collection<Epic> getEpics();
    void deleteTasks();
    void deleteEpics();
    void deleteSubtasks();
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);
    void addEpic(Epic epicTask);
    void addTask(Task task);
    void addSubtask(Subtask Subtask);
    void updateTask(Task task);
    void updateEpic(Epic epicTask);
    void updateSubtask(Subtask Subtask);
    void deleteTask(int id);
    void deleteEpic(int id);
    void deleteSubtask(int id);
    List<Subtask> getEpicSubtasks(int id);
    List<Task> getHistory();
    Collection<Task> getPrioritizedTasks();

}
