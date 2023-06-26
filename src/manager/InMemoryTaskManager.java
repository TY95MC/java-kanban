package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    protected final HashMap<Integer, Task> idToTasks = new HashMap<>();
    protected final HashMap<Integer, Epic> idToEpics = new HashMap<>();
    protected final HashMap<Integer, Subtask> idToSubtasks = new HashMap<>();

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Collection<Task> getTasks() {
        return Collections.unmodifiableCollection(idToTasks.values());
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return Collections.unmodifiableCollection(idToSubtasks.values());
    }

    @Override
    public Collection<Epic> getEpics() {
        return Collections.unmodifiableCollection(idToEpics.values());
    }

    @Override
    public void deleteTasks() {
        for (Integer id : idToTasks.keySet()) {
            historyManager.remove(id);
        }
        idToTasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Integer id : idToEpics.keySet()) {
            historyManager.remove(id);
        }
        for (Integer id : idToSubtasks.keySet()) {
            historyManager.remove(id);
        }
        idToEpics.clear();
        idToSubtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Integer id : idToSubtasks.keySet()) {
            historyManager.remove(id);
        }
        idToSubtasks.clear();
        for (Epic epic : idToEpics.values()) {
            epic.getSubtaskIds().clear();
            checkEpicTaskStatus(epic);
        }
    }

    @Override
    public Task getTask(int id) {
        Task task = idToTasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = idToEpics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = idToSubtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        checkEpicTaskStatus(epic);
        idToEpics.put(epic.getId(), epic);
    }

    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        idToTasks.put(task.getId(), task);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        Epic epic = idToEpics.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(generateId());
            int subtaskId = subtask.getId();
            epic.getSubtaskIds().add(subtaskId);
            idToSubtasks.put(subtaskId, subtask);
            checkEpicTaskStatus(epic);
        }
    }

    @Override
    public void updateTask(Task task) {
        if (idToTasks.containsKey(task.getId())) {
            idToTasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (idToEpics.containsKey(epic.getId())) {
            idToEpics.put(epic.getId(), epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Epic epic = idToEpics.get(subtask.getEpicId());
        int subtaskId = subtask.getId();
        if (idToSubtasks.containsKey(subtaskId) && // содержание подзадачи в хешмапе
                epic != null && // содержание эпика в хешмапе
                epic.getSubtaskIds().contains(subtaskId)) { // содержание подзадачи в эпике
            idToSubtasks.put(subtaskId, subtask);
            checkEpicTaskStatus(epic);
        }
    }

    @Override
    public void deleteTask(int id) {
        historyManager.remove(id);
        idToTasks.remove(id);
    }

    @Override
    public void deleteEpic(int id) {
        historyManager.remove(id);
        Epic epic = idToEpics.remove(id);
        if (epic != null) {
            for (Integer subtask : epic.getSubtaskIds()) {
                historyManager.remove(subtask);
                idToSubtasks.remove(subtask);
            }
        }
    }

    @Override
    public void deleteSubtask(int id) {
        historyManager.remove(id);
        final Subtask subtask = idToSubtasks.remove(id);
        if (subtask != null) {
            Epic epic = idToEpics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove((Integer) id);
            checkEpicTaskStatus(epic);
        }
    }

    @Override
    public List<Subtask> getEpicSubtasks(int id) {
        Epic epic = idToEpics.get(id);
        List<Subtask> subs = new ArrayList<>();
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = idToSubtasks.get(subtaskId);
                subs.add(subtask);
            }
        }
        return subs;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void checkEpicTaskStatus(Epic epic) {
        if (epic != null) {
            if (epic.getSubtaskIds().isEmpty()) {
                epic.setStatus(Status.NEW);
                return;
            }

            int subStatusNew = 0;
            int subStatusDone = 0;

            for (Integer subId : epic.getSubtaskIds()) {
                Status status = idToSubtasks.get(subId).getStatus();
                if (status.equals(Status.DONE)) {
                    subStatusDone++;
                } else if (status.equals(Status.NEW)) {
                    subStatusNew++;
                }
            }

            if (epic.getSubtaskIds().size() == subStatusNew) {
                epic.setStatus(Status.NEW);
            } else if (epic.getSubtaskIds().size() == subStatusDone) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    private int generateId() {
        return id++;
    }

    protected void setId(int id) {
        this.id = ++id;
    }

}
