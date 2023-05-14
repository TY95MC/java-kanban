import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> tasksHistory = new ArrayList<>();

    @Override
    public void addTaskToHistory(Task task) {
        if (task != null) {
            tasksHistory.add(task);
            while (tasksHistory.size() > 10) tasksHistory.remove(0);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return tasksHistory;
    }
}
