import java.util.Objects;

public class SubTask extends EpicTask{

    private EpicTask epic;

    public SubTask(String taskName, String taskDescription) {
        super(taskName, taskDescription);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        SubTask task = (SubTask) obj;
        return Objects.equals(taskName, task.taskName) && Objects.equals(taskDescription, task.taskDescription) &&
                Objects.equals(epic, task.epic) && Objects.equals(status, task.status) && id == task.id;
    }

    @Override
    public String toString() {
        return "{" +
                "SubTaskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", status=" + status + '\'' +
                ", id=" + id +
                ", EpicTask='" + epic.taskName +
                "'}\n";
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

    public EpicTask getEpic() {
        return epic;
    }

    public void setEpic(EpicTask epic) {
        this.epic = epic;
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
