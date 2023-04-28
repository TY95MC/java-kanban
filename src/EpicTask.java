import java.util.Objects;

public class EpicTask extends Task{

    public EpicTask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        EpicTask task = (EpicTask) obj;
        return Objects.equals(taskName, task.taskName) && Objects.equals(taskDescription, task.taskDescription) &&
                Objects.equals(status, task.status) && id == task.id;
    }

    @Override
    public String toString() {
        return "{" +
                "EpicTaskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status + '\'' +
                ", id=" + id +
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
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        switch (status) {
            case ("NEW") : status = "IN_PROGRESS"; break;
            case ("IN_PROGRESS") : status = "DONE"; break;
        }
        this.status = status;
    }
}
