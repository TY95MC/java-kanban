package task;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtaskIds  = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
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
