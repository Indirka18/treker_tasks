import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.InMemoryTaskManager;
import service.Managers;
import service.TaskManager;

public class main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        //2 задачи
        Task task1 = new Task("Переезд", "Собрать вещи", TaskStatus.NEW);
        Task task2 = new Task("Купить продукты", "Молоко, хлеб", TaskStatus.NEW);
        manager.addTask(task1);
        manager.addTask(task2);

        //1-й эпик с 2 подзадачами
        Epic epic1 = new Epic("Организовать переезд", "Переезд в новый офис");
        manager.createEpic(epic1);

        Subtask sub1 = new Subtask("Собрать коробки", "Коробки в гостиной",
                TaskStatus.NEW, epic1.getId());
        Subtask sub2 = new Subtask("Упаковать кошку", "Кошка в переноске",
                TaskStatus.NEW, epic1.getId());
        manager.addSubtask(sub1);
        manager.addSubtask(sub2);

        //2-й эпик с 1 подзадачей
        Epic epic2 = new Epic("Важный эпик 2", "Что-то важное");
        manager.createEpic(epic2);

        Subtask sub3 = new Subtask("Задача 1", "Описание задачи 1",
                TaskStatus.NEW, epic2.getId());
        manager.addSubtask(sub3);

        //Печатаем списки
        System.out.println("Epics");
        manager.getListEpic().forEach(System.out::println);

        System.out.println("\nTasks");
        manager.getListTask().forEach(System.out::println);

        System.out.println("\nSubtasks");
        manager.getListSubtask().forEach(System.out::println);

        //Меняем статусы
        System.out.println("\nChange status");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(task1);

        sub1.setStatus(TaskStatus.DONE);
        manager.updateSubtask(sub1);

        sub2.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateSubtask(sub2);

        System.out.println("\nЭпик 1 после изменения подзадач:");
        System.out.println(manager.getEpicById(epic1.getId()));

        System.out.println("\nЗадача 1 после изменения:");
        System.out.println(manager.getTaskById(task1.getId()));

        //Удаляем задачу и эпик
        System.out.println("\n Delete");
        manager.removeTask(task1.getId());
        manager.removeEpic(epic1.getId());

        System.out.println("После удаления:");
        System.out.println("Задачи: " + manager.getListTask().size());
        System.out.println("Эпики: " + manager.getListEpic().size());
        System.out.println("Подзадачи: " + manager.getListSubtask().size());
    }
}