import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private static int id = 1;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, EpicTask> epicTasks = new HashMap<>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<>();

    HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public ArrayList<EpicTask> getAllEpicTask() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpicTask() {
        epicTasks.clear();
        subTasks.clear();
    }

    @Override
    public void deleteAllSubTask() {
        subTasks.clear();
        for (EpicTask epic : epicTasks.values()) checkEpicTaskStatus(epic);
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.addTaskToHistory(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public EpicTask getEpicTaskById(int id) {
        historyManager.addTaskToHistory(epicTasks.get(id));
        return epicTasks.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        historyManager.addTaskToHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public EpicTask createEpicTask(EpicTask epicTask) {
        epicTask.setId(generateId());
        epicTasks.put(epicTask.getId(), epicTask);
        return epicTask;
    }

    @Override
    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        if (epicTasks.containsKey(subTask.getEpicId())) {
            subTask.setId(generateId());
            epicTasks.get(subTask.getEpicId()).getSubId().add(subTask.getId());
            subTasks.put(subTask.getId(), subTask);
            checkEpicTaskStatus(epicTasks.get(subTask.getEpicId()));
            return subTask;
        }
        return null;
    }

    @Override
    public Task updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            return task;
        }
        return null;
    }

    @Override
    public EpicTask updateEpicTask(EpicTask epicTask) {
        if (epicTasks.containsKey(epicTask.getId())){
            checkEpicTaskStatus(epicTask);
            epicTasks.put(epicTask.getId(), epicTask);
            return epicTask;
        }
        return null;
    }

    @Override
    public SubTask updateSubTask(SubTask subTask) {
        if (subTasks.containsKey(subTask.getId()) && // содержание подзадачи в хешмапе
                epicTasks.containsKey(subTask.getEpicId()) && // содержание эпика в хешмапе
                epicTasks.get(subTask.getEpicId()).getSubId().contains(subTask.getId())) { // содержание подзадачи в эпике
            subTasks.put(subTask.getId(), subTask);
            checkEpicTaskStatus(epicTasks.get(subTask.getEpicId()));
            return subTask;
        }
        return null;
    }

    @Override
    public Task removeTaskById(int id) {
            return tasks.remove(id);
    }

    @Override
    public EpicTask removeEpicTaskById(int id) {
        if(epicTasks.containsKey(id)) {
            for (Integer removeSubTask : epicTasks.get(id).getSubId()) {
                if (subTasks.containsKey(removeSubTask)){
                    subTasks.remove(removeSubTask);
                } else {
                    System.out.println("Подзадачи с id = " + removeSubTask + " нет.");
                }
            }
            return epicTasks.remove(id);
        }
        return null;
    }

    @Override
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

    @Override
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

    @Override
    public void checkEpicTaskStatus(EpicTask epicTask) {
        if (epicTask != null) {
            if (epicTask.getSubId().isEmpty()) {
                epicTask.setStatus(Status.NEW);
                return;
            }

            int subStatusNew = 0;
            int subStatusDONE = 0;
            
            for (Integer subId : epicTask.getSubId()) {
                if (subTasks.get(subId).getStatus().equals(Status.DONE)) {
                    subStatusDONE++;
                } else if (subTasks.get(subId).getStatus().equals(Status.NEW)) {
                    subStatusNew++;
                }
            }

            if (epicTask.getSubId().size() == subStatusNew) {
                epicTask.setStatus(Status.NEW);
            } else if (epicTask.getSubId().size() == subStatusDONE) {
                epicTask.setStatus(Status.DONE);
            } else {
                epicTask.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    @Override
    public int generateId() {
        return id++;
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

}
