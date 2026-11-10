import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.FileBackedTaskManager;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File file = new File("tasks.csv");
        
        FileBackedTaskManager manager1 = new FileBackedTaskManager(file);
        
        Task task1 = new Task("Task 1", "Описание", TaskStatus.NEW);
        manager1.addTask(task1);
        
        Epic epic1 = new Epic("Epic 1", "Описание эпика");
        manager1.createEpic(epic1);
        
        Subtask sub1 = new Subtask("Sub 1", "Описание", TaskStatus.NEW, epic1.getId());
        manager1.addSubtask(sub1);
        
        manager1.getTaskById(task1.getId());
        manager1.getEpicById(epic1.getId());
        
        FileBackedTaskManager manager2 = FileBackedTaskManager.loadFromFile(file);
        
        System.out.println("Задачи: " + manager2.getListTask());
        System.out.println("Эпики: " + manager2.getListEpic());
        System.out.println("Подзадачи: " + manager2.getListSubtask());
        System.out.println("История: " + manager2.history());
    }
}