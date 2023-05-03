import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;

    private HashMap<Integer, Task> tasksHashMap = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasksHashMap = new HashMap<>();
    private HashMap<Integer, SubTask> subTasksHashMap = new HashMap<>();

    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasksHashMap.values());
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasksHashMap.values());
    }

    public ArrayList<EpicTask> getAllEpicTask() {
        return new ArrayList<>(epicTasksHashMap.values());
    }

    public void deleteAllTask() {
        tasksHashMap.clear();
    }

    public void deleteAllEpicTask() {
        epicTasksHashMap.clear();
        subTasksHashMap.clear();
    }

    public void deleteAllSubTask() {
        subTasksHashMap.clear();
        for (EpicTask epic : epicTasksHashMap.values()) checkEpicTaskStatus(epic);
    }

    public Task getTaskById(int id) {
        return tasksHashMap.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        return epicTasksHashMap.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasksHashMap.get(id);
    }

    public EpicTask createEpicTask(EpicTask epicTask) {
        epicTask.setId(generateId());
        epicTasksHashMap.put(epicTask.id, epicTask);
        return epicTask;
    }

    public Task createTask(Task task) {
        task.setId(generateId());
        tasksHashMap.put(task.id, task);
        return task;
    }

    public SubTask createSubTask(SubTask subTask) {
        subTask.setId(generateId());
        subTasksHashMap.put(subTask.id, subTask);
        epicTasksHashMap.get(subTask.getEpicId()).getSubId().add(subTask.id);
        checkEpicTaskStatus(epicTasksHashMap.get(subTask.getEpicId()));
        return subTask;
    }

    public Task updateTask(Task task) {
        if (tasksHashMap.containsKey(task.getId())) {
            return tasksHashMap.put(task.id, task);
        }
        return null;
    }

    public EpicTask updateEpicTask(EpicTask epicTask) {
        if (epicTasksHashMap.containsKey(epicTask.getId())){
            checkEpicTaskStatus(epicTask);
            return epicTasksHashMap.put(epicTask.id, epicTask);
        }
        return null;
    }

    public SubTask updateSubTask(SubTask subTask) {
        if (subTasksHashMap.containsKey(subTask.getId()) && // содержание подзадачи в хешмапе
                epicTasksHashMap.containsKey(subTask.getEpicId()) && // содержание эпика в хешмапе
                epicTasksHashMap.get(subTask.getEpicId()).getSubId().contains(subTask.getId())) { // содержание подзадачи в эпике
            subTasksHashMap.put(subTask.id, subTask);
            checkEpicTaskStatus(epicTasksHashMap.get(subTask.getEpicId()));
            return subTask;
        }
        return null;
    }

    public Task removeTaskById(int id) {
        if (tasksHashMap.containsKey(id)) {
            return tasksHashMap.remove(id);
        }
        return null;
    }

    public EpicTask removeEpicTaskById(int id) {
        if(epicTasksHashMap.containsKey(id)) {
            for (Integer removeSubTask : epicTasksHashMap.get(id).getSubId()) {
                subTasksHashMap.remove(removeSubTask);
            }
            return epicTasksHashMap.remove(id);
        }
        return null;
    }

    public SubTask removeSubTaskById(int id) {
        if (subTasksHashMap.containsKey(id)) {
            int temp = subTasksHashMap.get(id).getEpicId();
            epicTasksHashMap.get(subTasksHashMap.get(id).getEpicId()).getSubId().remove((Integer) id);
            final SubTask removeTask = subTasksHashMap.remove(id);
            checkEpicTaskStatus(epicTasksHashMap.get(temp));
            return removeTask;
        }
        return null;
    }

    public ArrayList<SubTask> getSubTasksByEpicTask (EpicTask epicTask) {
        if (epicTasksHashMap.containsKey(epicTask.getId())) {
            ArrayList<SubTask> subs = new ArrayList<>();
            for (Integer subId : epicTask.getSubId()) {
                subs.add(subTasksHashMap.get(subId));
            }
            return subs;
        }
        return new ArrayList<>();
    }

    private void checkEpicTaskStatus(EpicTask epicTask) {
        if (epicTask != null) {
            int subStatusNew = 0;
            int subStatusDONE = 0;

            if (epicTask.getSubId().isEmpty()) {
                epicTask.setStatus("NEW");
                return;
            }

            for (Integer subId : epicTask.getSubId()) {
                if (subTasksHashMap.get(subId).getStatus().equals("DONE")) {
                    subStatusDONE++;
                } else if (subTasksHashMap.get(subId).getStatus().equals("NEW")) {
                    subStatusNew++;
                }
            }

            if (epicTask.getSubId().size() == subStatusNew) {
                epicTask.setStatus("NEW");
            } else if (epicTask.getSubId().size() == subStatusDONE) {
                epicTask.setStatus("DONE");
            } else {
                epicTask.setStatus("IN_PROGRESS");
            }
        }
    }

    private int generateId() {
        return id++;
    }
}
