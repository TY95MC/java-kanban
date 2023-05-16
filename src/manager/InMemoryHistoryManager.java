package manager;

import task.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> tasksHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            tasksHistory.add(task);
            if (tasksHistory.size() > 10) {
                tasksHistory.remove(0);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return Collections.unmodifiableList(tasksHistory);
    }

}
