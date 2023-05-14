import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task{

    public ArrayList<Integer> subId = new ArrayList<>();

    public EpicTask(String taskName, String taskDescription, Status status) {
        super(taskName, taskDescription, status);
    }

    public EpicTask(String taskName, String taskDescription, Status status,int id) {
        super(taskName, taskDescription, status, id);
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
                ", status='" + status + '\'' +
                ", id=" + id +
                ", subId=" + subId +
                "}\n";
    }

    public ArrayList<Integer> getSubId() {
        return subId;
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
}
