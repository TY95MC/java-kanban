package task;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtasksIdByEpic = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, Status status, int id) {
        super(name, description, status, id);
    }

    @Override
    public String toString() {
        return "{" +
                "EpicName='" + getTaskName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                ", subtasksIdByEpic=" + subtasksIdByEpic +
                "}\n";
    }

    public ArrayList<Integer> getSubtasksIdByEpic() {
        return subtasksIdByEpic;
    }

}
