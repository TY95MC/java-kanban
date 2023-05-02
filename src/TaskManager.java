import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;

    private HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTaskHashMap = new HashMap<>();
    private HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();

    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(taskHashMap.values());
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTaskHashMap.values());
    }

    public ArrayList<EpicTask> getAllEpicTask() {
        return new ArrayList<>(epicTaskHashMap.values());
    }

    public void deleteAllTask() {
        taskHashMap.clear();
    }

    public void deleteAllEpicTask() {
        epicTaskHashMap.clear();
        subTaskHashMap.clear();
    }

    public void deleteAllSubTask() {
        subTaskHashMap.clear();
        for (EpicTask epic : epicTaskHashMap.values()) epic.status = "NEW";
    }

    public Task getTaskById(int id) {
        return taskHashMap.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        return epicTaskHashMap.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTaskHashMap.get(id);
    }

    public EpicTask createEpicTask(EpicTask epicTask) {
        epicTask.setId(generateId());
        epicTask = new EpicTask(epicTask.getTaskName(), epicTask.getTaskDescription(),
                epicTask.getStatus(), epicTask.id, new HashMap<>());
        updateEpicTask(epicTask);
        epicTaskHashMap.put(epicTask.id, epicTask);
        return epicTask;
    }

    public Task createTask(Task task) {
        task.setId(generateId());
        task = new Task(task.getTaskName(), task.getTaskDescription(), task.getStatus(), task.id);
        taskHashMap.put(task.id, task);
        return task;
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateId());
        subTask = new SubTask(subTask.getTaskName(), subTask.getTaskDescription(),
                subTask.getStatus(), subTask.id, subTask.getEpicId());
        subTaskHashMap.put(subTask.id, subTask);
        epicTaskHashMap.get(subTask.getEpicId()).getEpicSubTasksHashMap().put(subTask.id, subTask);
        checkEpicTaskStatus(epicTaskHashMap.get(subTask.getEpicId()));
        return subTask;
    }

    public void updateTask(Task task) {
        if (taskHashMap.containsKey(task.getId())) {
            taskHashMap.put(task.id, task);
        }
    }

    public void updateEpicTask(EpicTask epicTask) {
        if (epicTaskHashMap.containsKey(epicTask.getId())){
            checkEpicTaskStatus(epicTask);
            epicTaskHashMap.put(epicTask.id, epicTask);
        }
    }

    public void updateSubTask(SubTask subTask) {
        if (subTaskHashMap.containsKey(subTask.getId()) &&
                epicTaskHashMap.containsKey(subTask.getEpicId()) &&
                epicTaskHashMap.get(subTask.getEpicId()).getEpicSubTasksHashMap().containsKey(subTask.getId())) {
            subTaskHashMap.put(subTask.id, subTask);
            epicTaskHashMap.get(subTask.getEpicId()).getEpicSubTasksHashMap().put(subTask.id, subTask);
            checkEpicTaskStatus(epicTaskHashMap.get(subTask.getEpicId()));
        }
    }

    public Task removeTaskById(int id) {
        taskHashMap.remove(id);
        return taskHashMap.get(id);
    }

    public EpicTask removeEpicTaskById(int id) {
        for (Integer removeSubTask : epicTaskHashMap.get(id).getEpicSubTasksHashMap().keySet()) {
            subTaskHashMap.remove(removeSubTask);
        }
        epicTaskHashMap.remove(id);
        return epicTaskHashMap.get(id);
    }

    public SubTask removeSubTaskById(int id) {
        int temp = subTaskHashMap.get(id).getEpicId();
        subTaskHashMap.remove(id);
        checkEpicTaskStatus(epicTaskHashMap.get(temp));
        return subTaskHashMap.get(id);
    }

    public ArrayList<SubTask> getSubTasksByEpicTask (EpicTask epicTask) {
        return new ArrayList<>(epicTask.getEpicSubTasksHashMap().values());
    }

    private void checkEpicTaskStatus(EpicTask epicTask) {
        int allSubTask = 0;
        int subStatusNew = 0;
        int subStatusDONE = 0;

        for (SubTask subTask : epicTask.getEpicSubTasksHashMap().values()) {
            allSubTask++;
            if (subTask.getStatus().equals("DONE")) {
                subStatusDONE++;
            } else if (subTask.getStatus().equals("NEW")) {
                subStatusNew++;
            }
        }

        if (allSubTask == subStatusNew) {
            epicTask.setStatus("NEW");
        } else if (allSubTask == subStatusDONE) {
            epicTask.setStatus("DONE");
        } else {
            epicTask.setStatus("IN_PROGRESS");
        }
    }

    private int generateId() {
        return id++;
    }
}
