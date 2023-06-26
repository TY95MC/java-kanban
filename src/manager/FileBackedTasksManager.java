package manager;

import task.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager{
    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    static FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager(file);
        List<String> tmp = Files.readAllLines(Path.of(file.getPath()));
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
                if (task instanceof Epic) {
                    manager.idToEpics.put(task.getId(), (Epic) task);
                } else if (task instanceof Subtask) {
                    manager.idToSubtasks.put(task.getId(), (Subtask) task);
                } else {
                    manager.idToTasks.put(task.getId(), task);
                }
            }
            i++;
        }

        if (tmp.size() >= 2 && tmp.get(tmp.size()-2).isEmpty() && !tmp.get(tmp.size()-1).isEmpty()) {
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

    static String historyToString(HistoryManager manager) {
        StringBuilder str = new StringBuilder();
        if (manager.getHistory().isEmpty()) {
            return "";
        }
        for (Task task : manager.getHistory()) {
            str.append(task.getId()).append(",");
        }
        str.deleteCharAt(str.lastIndexOf(","));
        return str.toString();
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> list = new ArrayList<>();
        String[] tmp = value.split(",");
        for (String s : tmp) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    private void save() {
        try {
            List<String> list = new ArrayList<>();
            final String COLUMNS = "id,type,name,status,description,epic";

            list.add(COLUMNS);
            for (Integer id : idToTasks.keySet()) {
                list.add(toString(idToTasks.get(id)));
            }
            for (Integer id : idToEpics.keySet()) {
                list.add(toString(idToEpics.get(id)));
            }
            for (Integer id : idToSubtasks.keySet()) {
                list.add(toString(idToSubtasks.get(id)));
            }
            list.add("");
            list.add(historyToString(historyManager));
            Writer fw = new FileWriter(file.getPath());
            for (String line : list) {
                fw.write(line + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ManagerSaveException("Сохранения не произошло!");
        }
    }

    private String toString(Task task) {
        String taskInLine;
        String description = task.getDescription();
        description = description.replace(",", "`");
        if (task instanceof Subtask) {
            taskInLine = String.join(",", String.valueOf(task.getId()), String.valueOf(task.getTaskType()),
                    task.getName(), String.valueOf(task.getStatus()), description,
                    String.valueOf(((Subtask) task).getEpicId()));
        } else {
            taskInLine = String.join(",", String.valueOf(task.getId()), String.valueOf(task.getTaskType()),
                    task.getName(), String.valueOf(task.getStatus()), description);
        }
        return taskInLine;
    }

    private Task taskFromString(String line) {
        String[] tmp = line.split(",");
        String name = tmp[2];
        String description = tmp[4];
        description = description.replace("`", ",");
        Status status = null;
        int id = Integer.parseInt(tmp[0]);

        switch (tmp[3]) {
            case ("NEW"):
                status = Status.NEW;
                break;
            case ("IN_PROGRESS"):
                status = Status.IN_PROGRESS;
                break;
            case ("DONE"):
                status = Status.DONE;
                break;
        }
        switch (tmp[1]) {
            case ("TASK"):
                return new Task(name, description, status, id);
            case ("EPIC"):
                return new Epic(name, description, status, id);
            case ("SUBTASK"):
                int epicId = Integer.parseInt(tmp[5]);
                idToEpics.get(epicId).getSubtaskIds().add(id);
                return new Subtask(name, description, status, id, epicId);
        }
        return null;
    }

    @Override
    public Collection<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public Collection<Epic> getEpics() {
        return super.getEpics();
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

    @Override
    public List<Subtask> getEpicSubtasks(int id) {
        return super.getEpicSubtasks(id);
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }
}
