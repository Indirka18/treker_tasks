package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();
    private final Map<Integer, Node> historyMap = new HashMap<>();

    private Node head;
    private Node tail;

    @Override
    public void add(Task task){
        if (task == null){
            return;
        }
        remove(task.getId());
        Node newNode = new Node(task);
        linkLast(newNode);
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id){
        Node node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public List<Task> getHistory(){
        return getTasks();
    }

    private void linkLast(Node node) {
        if (tail == null) {
            head = node;
        } else {
            tail.next = node;
            node.prev = tail; 
        tail = node;
        }
    }

    private List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }


}