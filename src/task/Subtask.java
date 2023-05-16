package task;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String taskName, String description, Status status, int epicId) {
        super(taskName, description, status);
        this.epicId = epicId;
    }

    public Subtask(String taskName, String description, Status status, int id, int epicId) {
        super(taskName, description, status, id);
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "{" +
                "SubtaskName='" + getTaskName() + '\'' +
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
