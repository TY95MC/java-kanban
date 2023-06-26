package task;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtaskIds  = new ArrayList<>();
    private final TaskType type = TaskType.EPIC;

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    public Epic(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }

    @Override
    public TaskType getTaskType() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "EpicName='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                ", subtaskIds=" + subtaskIds  +
                "}\n";
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds ;
    }

}
