import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        //2 задачи
        Task task1 = new Task("Задача 1", "Описание", TaskStatus.NEW);
        Task task2 = new Task("Задача 2", "Описание", TaskStatus.NEW);
        Task task3 = new Task("Задача 3", "Описание", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);
        manager.addTask(task3);

        //1-й эпик с 2 подзадачами
        Epic epic1 = new Epic("Эпик 1", "Описание");
        manager.createEpic(epic1);

        Subtask sub1 = new Subtask("Эпик 1. Подзадача 1", "Описание",
                TaskStatus.NEW, epic1.getId());
        Subtask sub2 = new Subtask("Эпик 1. Подзадача 2", "Описание",
                TaskStatus.NEW, epic1.getId());
        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        //4-й эпик без подзадач
        Epic epic2 = new Epic("Эпик 2(пустой)", "Нет подзадач");
        manager.createEpic(epic2);

        manager.getEpicById(epic2.getId()); // Эпик 2
        printHistory(manager); 

        manager.getTaskById(task1.getId());//Задача 1
        printHistory(manager);

        manager.getSubtaskById(sub2.getId());  //Подзадача 2
        printHistory(manager);

        manager.getTaskById(task1.getId()); // Задача 1
        printHistory(manager);
    }

    private static void printHistory(TaskManager manager) {
        System.out.println("Текущая история:");
        if (manager.history().isEmpty()) {
            System.out.println("   (пусто)");
        } else {
            for (Task task : manager.history()) {
                System.out.println("   -> " + task.getName());
            }
        }
        System.out.println();
    }
}