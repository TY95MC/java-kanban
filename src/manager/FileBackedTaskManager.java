package manager;

import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskType;
import task.Status;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final File file;
    protected final DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy|HH:mm");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        List<String> tmp = Files.readAllLines(file.toPath());
        if (tmp.size() == 0) {
            return manager;
        }
        int maxId = 0;
        int i = 1;
        while (i < tmp.size() && !tmp.get(i).isBlank()) {
            final Task task = manager.taskFromString(tmp.get(i));
            if (task != null) {
                if (task.getId() > maxId) {
                    maxId = task.getId();
                }
            }
            i++;
        }

        if (tmp.size() >= 2 && tmp.get(tmp.size() - 2).isEmpty() && !tmp.get(tmp.size() - 1).isEmpty()) {
            List<Integer> tmp1 = historyFromString(tmp.get(tmp.size()-1));
            for (Integer id : tmp1) {
                if (manager.idToTasks.containsKey(id)){
                    manager.historyManager.add(manager.idToTasks.get(id));
                }
                if (manager.idToEpics.containsKey(id)){
                    manager.historyManager.add(manager.idToEpics.get(id));
                }
                if (manager.idToSubtasks.containsKey(id)){
                    manager.historyManager.add(manager.idToSubtasks.get(id));
                }
            }
        }

        manager.setId(maxId);
        return manager;
    }

    private static String historyToString(HistoryManager manager) {
        StringBuilder str = new StringBuilder();
        if (manager.getHistory().isEmpty()) {
            return "";
        }
        for (Task task : manager.getHistory()) {
            str.append(task.getId()).append(",");
        }
        str.deleteCharAt(str.length() - 1);
        return str.toString();
    }

    private static List<Integer> historyFromString(String value) {
        List<Integer> list = new ArrayList<>();
        String[] tmp = value.split(",");
        for (String s : tmp) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    private void save() {
        try (Writer fw = new FileWriter(file.getPath(), StandardCharsets.UTF_8)) {
            List<String> list = new ArrayList<>();
            final String COLUMNS = "id,type,name,status,description,startTime,duration,endTime,epic";

            list.add(COLUMNS);
            for (Integer id : idToTasks.keySet()) {
                list.add(taskToString(idToTasks.get(id)));
            }
            for (Integer id : idToEpics.keySet()) {
                list.add(taskToString(idToEpics.get(id)));
            }
            for (Integer id : idToSubtasks.keySet()) {
                list.add(subtaskToString(idToSubtasks.get(id)));
            }
            list.add("");
            list.add(historyToString(historyManager));

            for (String line : list) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Сохранения не произошло!", e);
        }
    }

    private String taskToString(Task task) {
        String description = task.getDescription();
        description = description.replace(",", "`");
        String duration;
        if (task.getDuration() != null) {
            duration = LocalTime.of(0,0).plus(task.getDuration()).format(timeFormatter);
        } else {
            duration = LocalTime.of(0,0).format(timeFormatter);
        }
        try {
            return String.join(",", String.valueOf(task.getId()), String.valueOf(task.getTaskType()),
                    task.getName(), String.valueOf(task.getStatus()), description,
                    task.getStartTime().format(dataTimeFormatter), duration,
                    task.getEndTime().format(dataTimeFormatter));
        } catch (NullPointerException e) {
            return String.join(",", String.valueOf(task.getId()), String.valueOf(task.getTaskType()),
                    task.getName(), String.valueOf(task.getStatus()), description, "null", "null", "null");
        }
    }

    private String subtaskToString(Subtask subtask) {
        return taskToString(subtask) + "," + subtask.getEpicId();
    }

    private Task taskFromString(String line) {
        String[] tmp = line.split(",");
        String name = tmp[2];
        String description = tmp[4];
        description = description.replace("`", ",");
        Status status = Status.valueOf(tmp[3]);
        int id = Integer.parseInt(tmp[0]);
        LocalDateTime startTime;
        LocalDateTime endTime;
        Duration duration;
        try {
            startTime = LocalDateTime.parse(tmp[5], dataTimeFormatter);
            duration = Duration.between(LocalTime.parse(tmp[6], timeFormatter), startTime);
            endTime = LocalDateTime.parse(tmp[5], dataTimeFormatter);
        } catch (NullPointerException | DateTimeParseException e) {
            startTime = null;
            endTime = null;
            duration = null;
        }

        switch (TaskType.valueOf(tmp[1])) {
            case TASK:
                Task task = new Task(name, description, status, id);
                task.setStartTime(startTime);
                task.setDuration(duration);
                idToTasks.put(id, task);
                return task;
            case EPIC:
                Epic epic = new Epic(name, description, status, id);
                epic.setStartTime(startTime);
                epic.setDuration(duration);
                epic.setEndTime(endTime);
                idToEpics.put(id, epic);
                return epic;
            case SUBTASK:
                int epicId = Integer.parseInt(tmp[8]);
                Subtask subtask = new Subtask(name, description, status, id, epicId);
                subtask.setStartTime(startTime);
                subtask.setDuration(duration);
                idToEpics.get(epicId).getSubtaskIds().add(id);
                idToSubtasks.put(id, subtask);
                return subtask;
        }
        return null;
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    @Override
    public void deleteEpics() {
        super.deleteEpics();
        save();
    }

    @Override
    public void deleteSubtasks() {
        super.deleteSubtasks();
        save();
    }

    @Override
    public Task getTask(int id) {
        final Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public Epic getEpic(int id) {
        final Epic epic = super.getEpic(id);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = super.getSubtask(id);
        save();
        return subtask;
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

}
