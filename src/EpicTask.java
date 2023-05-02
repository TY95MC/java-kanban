import java.util.HashMap;
import java.util.Objects;

public class EpicTask extends Task{

    private HashMap<Integer, SubTask> subTasksHashMap = new HashMap<>();
    public EpicTask(String taskName, String taskDescription, String status) {
        super(taskName, taskDescription, status);
    }

    public EpicTask(String taskName, String taskDescription, String status,int id, HashMap<Integer, SubTask> subTasksHashMap) {
        super(taskName, taskDescription, status, id);
        this.subTasksHashMap = subTasksHashMap;
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
        this.status = status;
    }

    public HashMap<Integer, SubTask> getEpicSubTasksHashMap() {
        return subTasksHashMap;
    }

    public void setSubTaskHashMap(HashMap<Integer, SubTask> subTaskHashMap) {
        this.subTasksHashMap = subTaskHashMap;
    }
}
