package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private static int id = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public List<Task> getTasks() {
        List<Task> list = new ArrayList<>(tasks.values());
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Subtask> getSubtasks() {
        List<Subtask> list = new ArrayList<>(subtasks.values());
        return Collections.unmodifiableList(list);
    }

    @Override
    public List<Epic> getEpics() {
        List<Epic> list = new ArrayList<>(epics.values());
        return Collections.unmodifiableList(list);
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteSubtasks () {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksIdByEpic().clear();
            checkEpicTaskStatus(epic);
        }
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public Epic addEpic(Epic epicTask) {
        epicTask.setId(generateId());
        checkEpicTaskStatus(epicTask);
        epics.put(epicTask.getId(), epicTask);
        return epicTask;
    }

    @Override
    public Task addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (epics.containsKey(epicId)) {
            subtask.setId(generateId());
            epics.get(epicId).getSubtasksIdByEpic().add(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            checkEpicTaskStatus(epics.get(epicId));
            return subtask;
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
    public Epic updateEpic(Epic epicTask) {
        if (epics.containsKey(epicTask.getId())) {
            checkEpicTaskStatus(epicTask);
            epics.put(epicTask.getId(), epicTask);
            return epicTask;
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        int subId = subtask.getId();
        if (subtasks.containsKey(subId) && // содержание подзадачи в хешмапе
                epics.containsKey(subtask.getEpicId()) && // содержание эпика в хешмапе
                epic.getSubtasksIdByEpic().contains(subId)) { // содержание подзадачи в эпике
            subtasks.put(subId, subtask);
            checkEpicTaskStatus(epic);
            return subtask;
        }
        return null;
    }

    @Override
    public Task removeTaskById(int id) {
        return tasks.remove(id);
    }

    @Override
    public Epic removeEpicTaskById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.remove(id);
            for (Integer removeSubtask : epic.getSubtasksIdByEpic()) {
                if (subtasks.containsKey(removeSubtask)) {
                    subtasks.remove(removeSubtask);
                }
            }
            return epic;
        }
        return null;
    }

    @Override
    public Subtask removeSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            final Subtask removeTask = subtasks.remove(id);
            Epic epic = epics.get(removeTask.getEpicId());
            epic.getSubtasksIdByEpic().remove((Integer) id);
            checkEpicTaskStatus(epic);
            return removeTask;
        }
        return null;
    }

    @Override
    public List<Subtask> getEpicSubtasks (int id) {
        if (epics.containsKey(id)) {
            List<Subtask> subs = new ArrayList<>();
            for (Integer subId : epics.get(id).getSubtasksIdByEpic()) {
                subs.add(subtasks.get(subId));
            }
            return subs;
        }
        return new ArrayList<>();
    }

    private void checkEpicTaskStatus(Epic epicTask) {
        if (epicTask != null) {
            if (epicTask.getSubtasksIdByEpic().isEmpty()) {
                epicTask.setStatus(Status.NEW);
                return;
            }

            int subStatusNew = 0;
            int subStatusDONE = 0;
            
            for (Integer subId : epicTask.getSubtasksIdByEpic()) {
                if (subtasks.get(subId).getStatus().equals(Status.DONE)) {
                    subStatusDONE++;
                } else if (subtasks.get(subId).getStatus().equals(Status.NEW)) {
                    subStatusNew++;
                }
            }

            if (epicTask.getSubtasksIdByEpic().size() == subStatusNew) {
                epicTask.setStatus(Status.NEW);
            } else if (epicTask.getSubtasksIdByEpic().size() == subStatusDONE) {
                epicTask.setStatus(Status.DONE);
            } else {
                epicTask.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    private int generateId() {
        return id++;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
