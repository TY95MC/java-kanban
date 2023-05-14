import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getAllTask();
    ArrayList<SubTask> getAllSubTask();
    ArrayList<EpicTask> getAllEpicTask();
    void deleteAllTask();
    void deleteAllEpicTask();
    void deleteAllSubTask();
    Task getTaskById(int id);
    EpicTask getEpicTaskById(int id);
    SubTask getSubTaskById(int id);
    EpicTask createEpicTask(EpicTask epicTask);
    Task createTask(Task task);
    SubTask createSubTask(SubTask subTask);
    Task updateTask(Task task);
    EpicTask updateEpicTask(EpicTask epicTask);
    SubTask updateSubTask(SubTask subTask);
    Task removeTaskById(int id);
    EpicTask removeEpicTaskById(int id);
    SubTask removeSubTaskById(int id);
    ArrayList<SubTask> getSubTasksByEpicTask (EpicTask epicTask);
    void checkEpicTaskStatus(EpicTask epicTask);
    int generateId();
    ArrayList<Task> getHistory();

}
