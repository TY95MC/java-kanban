package manager;

import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    private final Comparator<Task> comparator = new Comparator<Task>() {
        @Override
        public int compare(Task t1, Task t2) {
            try {
                if (t1.getStartTime().isEqual(t2.getStartTime())) {
                    return 0;
                }
                return (t1.getStartTime().isBefore(t2.getStartTime())) ? 1 : -1;
            } catch (NullPointerException e) {
                return -1;
            }
        }
    };
    protected final HashMap<Integer, Task> idToTasks = new HashMap<>();
    protected final HashMap<Integer, Epic> idToEpics = new HashMap<>();
    protected final HashMap<Integer, Subtask> idToSubtasks = new HashMap<>();
    protected final TreeSet<Task> sortedSet = new TreeSet<>(comparator);

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
            sortedSet.remove(idToTasks.get(id));
            historyManager.remove(id);
        }
        idToTasks.clear();
    }

    @Override
    public void deleteEpics() {
        for (Integer id : idToEpics.keySet()) {
            sortedSet.remove(idToEpics.get(id));
            historyManager.remove(id);
        }
        for (Integer id : idToSubtasks.keySet()) {
            sortedSet.remove(idToSubtasks.get(id));
            historyManager.remove(id);
        }
        idToEpics.clear();
        idToSubtasks.clear();
    }

    @Override
    public void deleteSubtasks() {
        for (Integer id : idToSubtasks.keySet()) {
            sortedSet.remove(idToSubtasks.get(id));
            historyManager.remove(id);
        }
        idToSubtasks.clear();
        for (Epic epic : idToEpics.values()) {
            epic.getSubtaskIds().clear();
            checkEpicTaskStatus(epic);
            checkEpicPeriod(epic);
        }
    }

    @Override
    public Task getTask(int id) {
        Task task = idToTasks.get(id);
        if (task != null) {
            historyManager.add(task);
            return task;
        } else {
            throw new ManagerValidateException("Вы ввели неверный идентификатор!");
        }
    }

    @Override
    public Epic getEpic(int id) {
        Epic epic = idToEpics.get(id);
        if (epic != null) {
            historyManager.add(epic);
            return epic;
        } else {
            throw new ManagerValidateException("Вы ввели неверный идентификатор!");
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = idToSubtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
            return subtask;
        } else {
            throw new ManagerValidateException("Вы ввели неверный идентификатор!");
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        checkEpicTaskStatus(epic);
        checkEpicPeriod(epic);
        idToEpics.put(epic.getId(), epic);
    }

    @Override
    public void addTask(Task task) {
        if (validate(task)) {
            task.setId(generateId());
            idToTasks.put(task.getId(), task);
            sortedSet.add(task);
        } else {
            throw new ManagerValidateException("Новая задача пересекается с другими по времени выполнения!");
        }
    }

    @Override
    public void addSubtask(Subtask subtask) {
        Epic epic = idToEpics.get(subtask.getEpicId());
        if (epic != null && validate(subtask)) {
            subtask.setId(generateId());
            int subtaskId = subtask.getId();
            epic.getSubtaskIds().add(subtaskId);
            idToSubtasks.put(subtaskId, subtask);
            sortedSet.add(subtask);
            checkEpicTaskStatus(epic);
            checkEpicPeriod(epic);
        } else {
            throw new ManagerValidateException("Новая задача пересекается с другими по времени выполнения!");
        }
    }

    @Override
    public void updateTask(Task task) {
        if (idToTasks.containsKey(task.getId()) && validate(task)) {
            sortedSet.remove(task);
            idToTasks.put(task.getId(), task);
            sortedSet.add(task);
        } else {
            throw new ManagerValidateException("Задача пересекается с другими по времени выполнения!");
        }
    }

    @Override
    public void updateEpic(Epic epic) {
        if (idToEpics.containsKey(epic.getId())) {
            sortedSet.remove(epic);
            idToEpics.put(epic.getId(), epic);
            sortedSet.add(epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Epic epic = idToEpics.get(subtask.getEpicId());
        int subtaskId = subtask.getId();
        if (idToSubtasks.containsKey(subtaskId) && // содержание подзадачи в хешмапе
                epic != null && // содержание эпика в хешмапе
                epic.getSubtaskIds().contains(subtaskId) && // содержание подзадачи в эпике
                validate(subtask)) {
            sortedSet.remove(subtask);
            idToSubtasks.put(subtaskId, subtask);
            sortedSet.add(subtask);
            checkEpicTaskStatus(epic);
            checkEpicPeriod(epic);
        } else {
            throw new ManagerValidateException("Задача пересекается с другими по времени выполнения!");
        }
    }

    @Override
    public void deleteTask(int id) {
        if (idToTasks.containsKey(id)) {
            historyManager.remove(id);
            sortedSet.remove(idToTasks.remove(id));
        } else {
            throw new ManagerValidateException("Введен неверный идентификатор!");
        }
    }

    @Override
    public void deleteEpic(int id) {
        if(idToEpics.containsKey(id)) {
            historyManager.remove(id);
            final Epic epic = idToEpics.remove(id);
            sortedSet.remove(epic);
            for (Integer subtask : epic.getSubtaskIds()) {
            historyManager.remove(subtask);
            idToSubtasks.remove(subtask);
            }
        } else {
            throw new ManagerValidateException("Введен неверный идентификатор!");
        }
    }

    @Override
    public void deleteSubtask(int id) {
        if (idToSubtasks.containsKey(id)) {
            historyManager.remove(id);
            final Subtask subtask = idToSubtasks.remove(id);
            sortedSet.remove(subtask);
            Epic epic = idToEpics.get(subtask.getEpicId());
            epic.getSubtaskIds().remove((Integer) id);
            checkEpicTaskStatus(epic);
            checkEpicPeriod(epic);
        } else {
            throw new ManagerValidateException("Введен неверный идентификатор!");
        }
    }

    @Override
    public List<Subtask> getEpicSubtasks(int id) {
        if (idToEpics.containsKey(id)) {
            List<Subtask> subs = new ArrayList<>();
            for (Integer subtaskId : idToEpics.get(id).getSubtaskIds()) {
                Subtask subtask = idToSubtasks.get(subtaskId);
                subs.add(subtask);
            }
            return subs;
        } else {
            throw new ManagerValidateException("Введен неверный идентификатор большой задачи!");
        }
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
        } else {
            throw new ManagerValidateException("Ошибка расчета статуса задачи!");
        }
    }

    private void checkEpicPeriod(Epic epic) {
        if (epic != null) {
            if (epic.getSubtaskIds().isEmpty()) {
                epic.setStartTime(null);
                epic.setEndTime(null);
                epic.setDuration(null);
                return;
            }

            LocalDateTime startTime = LocalDateTime.MAX;
            LocalDateTime endTime = LocalDateTime.MIN;

            for (Integer id : epic.getSubtaskIds()) {
                Subtask subtask = idToSubtasks.getOrDefault(id,
                        new Subtask("", "", Status.NEW, epic.getId()));
                if (subtask.getStartTime() != null && startTime.isAfter(subtask.getStartTime())) {
                    startTime = subtask.getStartTime();
                }
                if (subtask.getEndTime() != null && endTime.isBefore(subtask.getEndTime())) {
                    endTime = subtask.getEndTime();
                }
            }

            if (!startTime.isEqual(LocalDateTime.MAX)) {
                epic.setStartTime(startTime);
            }
            if (!endTime.isEqual(LocalDateTime.MIN)) {
                epic.setEndTime(endTime);
            }
            if (!startTime.isEqual(LocalDateTime.MAX) && !endTime.isEqual(LocalDateTime.MIN)) {
                epic.setDuration(Duration.between(startTime, endTime));
            }
        }
    }

    public boolean validate(Task entryTask) {
        boolean flag = false;
        if (getPrioritizedTasks().isEmpty()) {
            return true;
        }
        if (entryTask.getStartTime() == null && entryTask.getEndTime() == null) {
            return true;
        }
        for (Task task : getPrioritizedTasks()) {
            if (task.getStartTime() == null && task.getEndTime() == null) {
                flag = true;
                continue;
            }
            if (entryTask.getStartTime().isBefore(task.getStartTime())
                    && entryTask.getEndTime().isBefore(task.getStartTime())
                    || entryTask.getStartTime().isAfter(task.getEndTime())
                    && entryTask.getEndTime().isAfter(task.getEndTime())) {
                return true;
            }
        }
        return flag;
    }

    public Collection<Task> getPrioritizedTasks() {
        return sortedSet.stream().sorted(comparator).collect(Collectors.toUnmodifiableList());
    }

    private int generateId() {
        return id++;
    }

    protected void setId(int newId) {
        id = ++newId;
    }

}
