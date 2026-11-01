package service;

import model.Task;
import java.util.List;

public interface HistoryManager {
    // Пометить задачу как просмотренную
    void add(Task task); 
    
    // Получить список просмотренных задач
    List<Task> getHistory(); 
}