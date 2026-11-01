package service;
import model.Epic;
import model.Subtask;
import model.Task;
import java.util.List;

public interface TaskManager{
    // Для Task
    List<Task> getListTask();
    void deleteAllTasks();
    Task getTaskById(int id);
    void addTask(Task task);
    void updateTask(Task task);
    void removeTask(int id);

    // Для Subtask
    List<Subtask> getListSubtask();
    void deleteAllSubtasks();
    Subtask getSubtaskById(int id); 
    void addSubtask(Subtask subtask);
    void updateSubtask(Subtask subtask);
    void removeSubtask(int id);

    // Для Epic
    List<Epic> getListEpic();
    void deleteAllEpics();
    Epic getEpicById(int id);
    void createEpic(Epic epic);
    void updateEpic(Epic epic);
    void removeEpic(int id);
    List<Subtask> getSubtaskInEpic(int idEpic);



}