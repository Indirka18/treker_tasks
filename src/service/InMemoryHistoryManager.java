package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    @Override
    public void add(Task task){
        if (task == null){
            return;
        }
        remove(task.getId());
        history.add(task);
        if (history.size() > 10){
            history.remove(0);
        }
    }

    @Override
    public void remove(int id){
        for (int i = 0; i < history.size(); i++){
            if (history.get(i).getId() == id){
                history.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Task> getHistory(){
        return new ArrayList<>(history);
    }
}