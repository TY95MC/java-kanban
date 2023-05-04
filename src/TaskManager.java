import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    public ArrayList<EpicTask> getAllEpicTask() {
        return new ArrayList<>(epicTasks.values());
    }

    public void deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllEpicTask() {
        epicTasks.clear();
        subTasks.clear();
    }

    public void deleteAllSubTask() {
        subTasks.clear();
        for (EpicTask epic : epicTasks.values()) checkEpicTaskStatus(epic);
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        return epicTasks.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public EpicTask createEpicTask(EpicTask epicTask) {
        epicTask.setId(generateId());
        return epicTasks.put(epicTask.id, epicTask);
    }

    public Task createTask(Task task) {
        task.setId(generateId());
        return tasks.put(task.id, task);
    }

    public SubTask createSubTask(SubTask subTask) {
        if (epicTasks.containsKey(subTask.getEpicId())) {
            subTask.setId(generateId());
            epicTasks.get(subTask.getEpicId()).getSubId().add(subTask.id);
            checkEpicTaskStatus(epicTasks.get(subTask.getEpicId()));
            return subTasks.put(subTask.id, subTask);
        }
        return null;
    }

    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            return tasks.put(task.id, task);
        }
        return null;
    }

    public EpicTask updateEpicTask(EpicTask epicTask) {
        if (epicTasks.containsKey(epicTask.getId())){
            checkEpicTaskStatus(epicTask);
            return epicTasks.put(epicTask.id, epicTask);
        }
        return null;
    }

    public SubTask updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId()) && // содержание подзадачи в хешмапе
                epicTasks.containsKey(subTask.getEpicId()) && // содержание эпика в хешмапе
                epicTasks.get(subTask.getEpicId()).getSubId().contains(subTask.getId())) { // содержание подзадачи в эпике
            subTasks.put(subTask.id, subTask);
            checkEpicTaskStatus(epicTasks.get(subTask.getEpicId()));
            return subTask;
        }
        return null;
    }

    public Task removeTaskById(int id) {
            return tasks.remove(id);
    }

    public EpicTask removeEpicTaskById(int id) {
        if(epicTasks.containsKey(id)) {
            for (Integer removeSubTask : epicTasks.get(id).getSubId()) {
                subTasks.remove(removeSubTask);
            }
            return epicTasks.remove(id);
        }
        return null;
    }

    public SubTask removeSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            int temp = subTasks.get(id).getEpicId();
            epicTasks.get(subTasks.get(id).getEpicId()).getSubId().remove((Integer) id);
            final SubTask removeTask = subTasks.remove(id);
            checkEpicTaskStatus(epicTasks.get(temp));
            return removeTask;
        }
        return null;
    }

    public ArrayList<SubTask> getSubTasksByEpicTask (EpicTask epicTask) {
        if (epicTasks.containsKey(epicTask.getId())) {
            ArrayList<SubTask> subs = new ArrayList<>();
            for (Integer subId : epicTask.getSubId()) {
                subs.add(subTasks.get(subId));
            }
            return subs;
        }
        return new ArrayList<>();
    }

    private void checkEpicTaskStatus(EpicTask epicTask) {
        if (epicTask != null) {
            if (epicTask.getSubId().isEmpty()) {
                epicTask.setStatus("NEW");
                return;
            }

            int subStatusNew = 0;
            int subStatusDONE = 0;
            
            for (Integer subId : epicTask.getSubId()) {
                if (subTasks.get(subId).getStatus().equals("DONE")) {
                    subStatusDONE++;
                } else if (subTasks.get(subId).getStatus().equals("NEW")) {
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
