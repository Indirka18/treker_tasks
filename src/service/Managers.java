package service;

public class Managers {

    // Единственный метод — возвращает менеджер задач
    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }
}