package manager;

import task.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList<Task> tasksHistory = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        final Node<Task> node = new Node<>(task);
        tasksHistory.linkLast(task.getId(), node);
    }

    @Override
    public void remove(int id) {
        tasksHistory.removeNode(id);
    }

    @Override
    public List<Task> getHistory() {
        return Collections.unmodifiableList(tasksHistory.getTasks());
    }

    private static class CustomLinkedList<T> {
        public Node<T> head;
        public Node<T> tail;
        private int size = 0;
        private final HashMap<Integer, Node<T>> idToNodes = new HashMap<>();

        private void linkLast(int id, Node<T> t) {
            final Node<T> n = tail;
            tail = t;
            if (n == null) {
                head = t;
            } else {
                n.next = t;
                t.prev = n;
            }
            idToNodes.put(id, t);
            if (!idToNodes.containsKey(id)) {
                size++;
            }
        }

        private List<T> getTasks() {
            List<T> list = new ArrayList<>();
            for (Node<T> node : idToNodes.values()) {
                list.add(node.data);
            }
            return list;
        }

        private void removeNode(int id) {
            final Node<T> node = idToNodes.get(id);
            if (node != null) {
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
                idToNodes.remove(id);
                size--;
            }
        }

        public int size() {
            return size;
        }
    }

    private static class Node<T> {
        public T data;
        public Node<T> next;
        public Node<T> prev;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }

}
