package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>();
    private final HashMap<Integer, Node<Task>> idToNodes = new HashMap<>();

    @Override
    public void add(Task task) {
        final Node<Task> node = new Node<>(task);

        if (idToNodes.containsKey(task.getId())) {
            tasksHistory.removeNode(node);
        }
        tasksHistory.linkLast(node);
        idToNodes.put(task.getId(), node);
    }

    @Override
    public void remove(int id) {
        if (idToNodes.containsKey(id)) {
            final Node<Task> node = idToNodes.get(id);
            tasksHistory.removeNode(node);
            idToNodes.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return Collections.unmodifiableList(tasksHistory.tasks);
    }

    public class CustomLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;
        private int size = 0;
        private final List<T> tasks = new ArrayList<>();

        private void linkLast(Node<T> t) {
            final Node<T> n = tail;
            tail = t;
            if (n == null) {
                head = t;
            } else {
                n.next = t;
                t.prev = n;
            }
            getTasks(t);
            size++;
        }

        private void getTasks(Node<T> t) {
            tasks.add(t.data);
        }

        private void removeNode(Node<T> node) {
            tasks.remove(node.data);
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                node.next = null;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                node.prev = null;
            }
            size--;
        }

        public int size() {
            return size;
        }
    }

}
