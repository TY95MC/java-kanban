import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    int id = 1;

    HashMap<Integer, Task> taskHashMap = new HashMap<>();
    HashMap<Integer, EpicTask> epicTaskHashMap = new HashMap<>();
    HashMap<Integer, SubTask> subTaskHashMap = new HashMap<>();

    public ArrayList<Task> getAllTask() {
        System.out.println("Получение списка задач.");
        ArrayList<Task> taskArrayList = new ArrayList<>();
        if (!taskHashMap.isEmpty()) {
            taskArrayList.addAll(taskHashMap.values());
        }
        return taskArrayList;
    }

    public ArrayList<SubTask> getAllSubTask() {
        System.out.println("Получение списка подзадач.");
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
        if (!subTaskHashMap.isEmpty()) {
            subTaskArrayList.addAll(subTaskHashMap.values());
        }
        return subTaskArrayList;
    }

    public ArrayList<EpicTask> getAllEpicTask() {
        System.out.println("Получение списка больших задач.");
        ArrayList<EpicTask> epicTaskArrayList = new ArrayList<>();
        if (!epicTaskHashMap.isEmpty()) {
            epicTaskArrayList.addAll(epicTaskHashMap.values());
        }
        return epicTaskArrayList;
    }

    public void deleteAllTask() {
        System.out.println("Удаление списка задач.");
        taskHashMap.clear();
    }

    public void deleteAllEpicTask() {
        System.out.println("Удаление списка больших задач.");
        epicTaskHashMap.clear();
        subTaskHashMap.clear();
    }

    public void deleteAllSubTask() {
        System.out.println("Удаление списка подзадач.");
        subTaskHashMap.clear();
    }

    public Task getTaskById(int id) {
        System.out.println("Получение задач по ID.");
        return taskHashMap.get(id);
    }

    public EpicTask getEpicTaskById(int id) {
        System.out.println("Получение большой задачи по ID.");
        EpicTask epicTaskById = null;
        for (EpicTask epicTask : epicTaskHashMap.values()) {
             if (epicTask.getId() == id) {
                 epicTaskById = epicTask;
             }
        }
        return epicTaskById;
    }



    public SubTask getSubTaskById(int id) {
        System.out.println("Получение подзадачи по ID.");
        return subTaskHashMap.get(id);
    }

    public EpicTask createEpicTask(EpicTask epicTask) {
        System.out.println("Создание большой задачи.");
        epicTask.setId(generateId());
        epicTaskHashMap.put(epicTask.getId(), epicTask);
        return epicTask;
    }

    public Task createTask(Task task) {
        System.out.println("Создание задачи.");
        task.setId(generateId());
        taskHashMap.put(task.id, task);
        return task;
    }

    public SubTask createSubTask(EpicTask epicTask, SubTask subTask) {
        System.out.println("Создание подзадачи.");
        subTask.setEpic(epicTask);
        subTask.setId(generateId());
        subTaskHashMap.put(subTask.id, subTask);
        return subTask;
    }

    public Task updateTask(Task task) {
        System.out.println("Обновление задачи.");
        task.setStatus(task.getStatus());
        taskHashMap.put(task.id, task);
        return task;
    }

    public EpicTask updateEpicTask(EpicTask epicTask) {
        System.out.println("Обновление большой задачи.");
        epicTask.setStatus(epicTask.getStatus());
        checkEpicTaskStatus(epicTask);
        epicTaskHashMap.put(epicTask.id, epicTask);
        return epicTask;
    }

    public SubTask updateSubTask(EpicTask epicTask, SubTask subTask) {
        System.out.println("Обновление подзадачи.");
        subTask.setStatus(subTask.getStatus());
        subTaskHashMap.put(subTask.id, subTask);
        checkEpicTaskStatus(epicTask);
        return subTask;
    }

    public void removeTaskById(int id) {
        System.out.println("Удаление задачи по ID.");
        taskHashMap.remove(id);
    }

    public void removeEpicTaskById(int id) {
        System.out.println("Удаление большой задачи по ID.");
        ArrayList<Integer> subTaskIdByEpic = new ArrayList<>();
        for (SubTask subTask : subTaskHashMap.values()) {
            if (subTask.getEpic().equals(epicTaskHashMap.get(id))) {
                subTaskIdByEpic.add(subTask.id);
            }
        }
        for (Integer subTaskId : subTaskIdByEpic) {
            subTaskHashMap.remove(subTaskId);
        }
        epicTaskHashMap.remove(id);
    }

    public void removeSubTaskById(int id) {
        System.out.println("Удаление подзадачи по ID.");
        subTaskHashMap.remove(id);
    }

    public ArrayList<SubTask> getSubTasksByEpicTask (EpicTask epicTask) {
        System.out.println("Получение задач по большой задаче.");
        ArrayList<SubTask> subTaskArrayList = new ArrayList<>();
        for (SubTask subTask : subTaskHashMap.values()) {
            if (subTask.getEpic().equals(epicTask)) {
                subTaskArrayList.add(subTask);
            }
        }
        return subTaskArrayList;
    }

    public void checkEpicTaskStatus(EpicTask epicTask) {
        System.out.println("Проверка статуса большой задачи.");
        int allSub = 0;
        int subStatusDONE = 0;
        for (SubTask subTask : subTaskHashMap.values()) {
            if (subTask.getEpic().equals(epicTask)) {
                allSub++;
                if(subTask.getStatus().equals("DONE")) {
                    subStatusDONE++;
                }
            }
        }
        if (allSub == subStatusDONE) {
            epicTask.setStatus("DONE");
        }
    }

    public int generateId() {
        return id++;
    }
}
