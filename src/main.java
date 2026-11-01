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

        //2-й эпик с 1 подзадачей
        Epic epic2 = new Epic("Важный эпик 2", "Что-то важное");
        manager.createEpic(epic2);

        Subtask sub3 = new Subtask("Эпик 2. Подзадача 1", "Описание задачи 1",
                TaskStatus.NEW, epic2.getId());
        manager.addSubtask(sub3);

        //3-ий эпик с 3 подзадачами
        Epic epic3 = new Epic("Эпик 3", "3 подзадачи");
        manager.createEpic(epic3);

        Subtask sub4 = new Subtask("Эпик 3. Подзадача 1", "Описание", TaskStatus.NEW, epic3.getId());
        Subtask sub5 = new Subtask("Эпик 3. Подзадача 2", "Описание", TaskStatus.NEW, epic3.getId());
        Subtask sub6 = new Subtask("Эпик 3. Подзадача 3", "Описание", TaskStatus.NEW, epic3.getId());
        manager.addSubtask(sub4);
        manager.addSubtask(sub5);
        manager.addSubtask(sub6);

        //4-й эпик без подзадач
        Epic epic4 = new Epic("Эпик 4(пустой)", "Нет подзадач");
        manager.createEpic(epic4);

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

        System.out.println("\nTest history\n");

        System.out.println("1. Запрашиваем Задачу 1:");
        manager.getTaskById(task1.getId());
        printHistory(manager);

        System.out.println("2. Запрашиваем Эпик 1:");
        manager.getEpicById(epic1.getId());
        printHistory(manager);

        System.out.println("3. Запрашиваем Подзадачу 1:");
        manager.getSubtaskById(sub1.getId());
        printHistory(manager);

        System.out.println("4. Запрашиваем Задачу 1 ПОВТОРНО:");
        manager.getTaskById(task1.getId());
        printHistory(manager);

        System.out.println("5. Запрашиваем Задачу 2:");
        manager.getTaskById(task2.getId());
        printHistory(manager);

        System.out.println("6. Запрашиваем Эпик 2:");
        manager.getEpicById(epic2.getId());
        printHistory(manager);

        System.out.println("7. Запрашиваем Подзадачу 3:");
        manager.getSubtaskById(sub3.getId());
        printHistory(manager);

        // Удаляем задачу и эпик
        System.out.println("\n Delete from history");
        manager.getTaskById(task1.getId());
        manager.getEpicById(epic2.getId());
        System.out.println("History before: ");
        printHistory(manager);

        manager.removeTask(task1.getId());
        manager.removeEpic(epic2.getId());
        System.out.println("History after: ");
        printHistory(manager);

        System.out.println("Задачи: " + manager.getListTask().size());
        System.out.println("Эпики: " + manager.getListEpic().size());
        System.out.println("Подзадачи: " + manager.getListSubtask().size());

         // 4. Запрашиваем задачи в разном порядке
         System.out.println("1. Смотрим Задачу 2");
         manager.getTaskById(task2.getId());
         printHistory(manager);
 
         System.out.println("2. Смотрим Эпик с 3-мя подзадачами");
         manager.getEpicById(epic3.getId());
         printHistory(manager);
 
         System.out.println("3. Смотрим Подзадачу 1 3-го эпика");
         manager.getSubtaskById(sub4.getId());
         printHistory(manager);
 
         System.out.println("4. Смотрим Задачу 2(повторно)");
         manager.getTaskById(task2.getId());
         printHistory(manager);
 
         System.out.println("5. Смотрим Подзадачу 3 3-го эпика");
         manager.getSubtaskById(sub6.getId());
         printHistory(manager);
 
         System.out.println("6. Смотрим Задачу 3");
         manager.getTaskById(task3.getId());
         printHistory(manager);
 
         System.out.println("7. Смотрим Эпик без подзадач");
         manager.getEpicById(epic4.getId());
         printHistory(manager);
 
         System.out.println("8. Смотрим Подзадачу 6");
         manager.getSubtaskById(sub6.getId());
         printHistory(manager);
 
         // 5. Удаляем задачу, которая есть в истории
         System.out.println("=== УДАЛЕНИЕ ЗАДАЧИ 1 (она была в истории) ===");
         manager.removeTask(task1.getId());
         printHistory(manager);
         // Задачи 1 быть не должно!
 
         // 6. Удаляем эпик с тремя подзадачами
         System.out.println("=== УДАЛЕНИЕ БОЛЬШОГО ЭПИКА (вместе с 3 подзадачами) ===");
         manager.removeEpic(epic3.getId());
         printHistory(manager);
    }

    private static void printHistory(TaskManager manager) {
        System.out.println("History");
        if (manager.history().isEmpty()) {
            System.out.println("   (пусто)");
        } else {
            for (Task task : manager.history()) {
                System.out.println("   -> " + task.getName() + " (ID: " + task.getId() + ")");
            }
        }
        System.out.println();
    }
}