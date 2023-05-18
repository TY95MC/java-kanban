package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public Collection<Task> getTasks() {
        return Collections.unmodifiableCollection(tasks.values());
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return Collections.unmodifiableCollection(subtasks.values());
    }

    @Override
    public Collection<Epic> getEpics() {
        return Collections.unmodifiableCollection(epics.values());
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
    public void deleteSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
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
    public Epic addEpic(Epic epic) {
        epic.setId(generateId());
        checkEpicTaskStatus(epic);
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Task addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Subtask addSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            subtask.setId(generateId());
            int subtaskId = subtask.getId();
            epic.getSubtaskIds().add(subtaskId);
            subtasks.put(subtaskId, subtask);
            checkEpicTaskStatus(epic);
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
    public Epic updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            return epic;
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        int subtaskId = subtask.getId();
        if (subtasks.containsKey(subtaskId) && // содержание подзадачи в хешмапе
                epic != null && // содержание эпика в хешмапе
                epic.getSubtaskIds().contains(subtaskId)) { // содержание подзадачи в эпике
            subtasks.put(subtaskId, subtask);
            checkEpicTaskStatus(epic);
            return subtask;
        }
        return null;
    }

    @Override
    public Task deleteTask(int id) {
        return tasks.remove(id);
    }

    @Override
    public Epic deleteEpic(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Integer subtask : epic.getSubtaskIds()) {
                subtasks.remove(subtask);
            }
            return epic;
        }
        return null;
    }

    @Override
    public Subtask deleteSubtask(int id) {
        final Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove((Integer) id);
            checkEpicTaskStatus(epic);
            return subtask;
        }
        return null;
    }

    @Override
    public List<Subtask> getEpicSubtasks(int id) {
        Epic epic = epics.get(id);
        List<Subtask> subs = new ArrayList<>();
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subs.add(subtasks.get(subtaskId));
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
                Status status = subtasks.get(subId).getStatus();
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

}
