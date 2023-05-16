package manager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getTasks();
    List<Subtask> getSubtasks();
    List<Epic> getEpics();
    void deleteTasks();
    void deleteEpics();
    void deleteSubtasks();
    Task getTask(int id);
    Epic getEpic(int id);
    Subtask getSubtask(int id);
    Epic addEpic(Epic epicTask);
    Task addTask(Task task);
    Subtask addSubtask(Subtask Subtask);
    Task updateTask(Task task);
    Epic updateEpic(Epic epicTask);
    Subtask updateSubtask(Subtask Subtask);
    Task removeTaskById(int id);
    Epic removeEpicTaskById(int id);
    Subtask removeSubtaskById(int id);
    List<Subtask> getEpicSubtasks(int id);
    List<Task> getHistory();

}
