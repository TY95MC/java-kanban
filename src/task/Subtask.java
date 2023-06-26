package task;

public class Subtask extends Task {

    private int epicId;
    private final TaskType type = TaskType.SUBTASK;

    public Subtask(String taskName, String description, Status status, int epicId) {
        super(taskName, description, status);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String description, Status status, int id, int epicId) {
        super(taskName, description, status, id);
        this.epicId = epicId;
    }

    @Override
    public TaskType getTaskType() {
        return type;
    }

    @Override
    public String toString() {
        return "{" +
                "SubtaskName='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                ", epicId=" + epicId +
                "}\n";
    }


    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

}
