import java.util.Objects;

public class SubTask extends Task{

    private int epicId;

    public SubTask(String taskName, String taskDescription, Status status, int epicId) {
        super(taskName, taskDescription, status);
        this.epicId = epicId;
    }

    public SubTask(String taskName, String taskDescription, Status status, int id, int epicId) {
        super(taskName, taskDescription, status, id);
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        SubTask task = (SubTask) obj;
        return Objects.equals(taskName, task.taskName) && Objects.equals(taskDescription, task.taskDescription) &&
                Objects.equals(epicId, task.epicId) && Objects.equals(status, task.status) && id == task.id;
    }

    @Override
    public String toString() {
        return "{" +
                "SubTaskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                ", EpicTaskId=" + epicId +
                "}\n";
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    @Override
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String getTaskDescription() {
        return taskDescription;
    }

    @Override
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
