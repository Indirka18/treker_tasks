package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import service.TaskType;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    // Превращает задачу в строку CSV
    public String toString(Task task) {
        TaskType type;
        String idEpic = "";

        if (task instanceof Subtask) {
            type = TaskType.SUBTASK;
            idEpic = String.valueOf(((Subtask) task).getIdEpic());
        } else if (task instanceof Epic) {
            type = TaskType.EPIC;
        } else {
            type = TaskType.TASK;
        }

        return task.getId() + "," + type + "," + task.getName() + "," + 
               task.getStatus() + "," + task.getDetails() + "," + idEpic + "\n";
    }

    // Создает задачу из строки CSV
    public Task fromString(String value) {
        String[] mas = value.split(",");
        
        int id = Integer.parseInt(mas[0]);
        String type = mas[1];
        String name = mas[2];
        TaskStatus status = TaskStatus.valueOf(mas[3]);
        String details = mas[4];

        if (type.equals("TASK")) {
            Task task = new Task(name, details, status);
            task.setId(id);
            return task;
        } else if (type.equals("SUBTASK")) {
            int epicId = Integer.parseInt(mas[5]);
            Subtask subtask = new Subtask(name, details, status, epicId);
            subtask.setId(id);
            return subtask;
        } else {
            Epic epic = new Epic(name, details);
            epic.setId(id);
            epic.setStatus(status);
            return epic;
        }
    }

    public void save() {
        try {
            StringBuilder st = new StringBuilder("id,type,name,status,description,epic\n");

            List<Task> tasks = getListTask();
            for (Task task : tasks) {
                st.append(toString(task));
            }

            List<Subtask> subs = getListSubtask();
            for (Subtask sub : subs) {
                st.append(toString(sub));
            }

            List<Epic> epics = getListEpic();
            for (Epic epic : epics) {
                st.append(toString(epic));
            }
            
            st.append("\n");
            List<Task> h = history();
            for (Task task : h) {
                st.append(task.getId()).append(",");
            }

            Files.writeString(file.toPath(), st, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при записи в файл", e);
        }
    }

    @Override
    public void addTask(Task task){
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task){
        super.updateTask(task);
        save();
    }

    @Override
    public void removeTask(int id){
        super.removeTask(id);
        save();
    }

    @Override
    public void createEpic(Epic epic){
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic){
        super.updateEpic(epic);
        save();
    }

    @Override
    public void removeEpic(int id){
        super.removeEpic(id);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask){
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask){
        super.updateSubtask(subtask);
        save();
    }
    
    @Override
    public void removeSubtask(int id){
        super.removeSubtask(id); 
        save();
    }

    // Восстановление из файла
    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try {
            List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            boolean isHistory = false;
            
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                
                if (line.isEmpty()) {
                    isHistory = true;
                    continue;
                }
                
                if (isHistory) {
                    // Восстановление истории
                    String[] ids = line.split(",");
                    for (String idStr : ids) {
                        if (!idStr.isEmpty()) {
                            int id = Integer.parseInt(idStr);
                            Task t = manager.getTaskById(id);
                            if (t == null) t = manager.getEpicById(id);
                            if (t == null) t = manager.getSubtaskById(id);
                        }
                    }
                } else {
                    // Восстановление задач
                    Task task = manager.fromString(line);
                    if (task instanceof Subtask) {
                        manager.subtasks.put(task.getId(), (Subtask) task);
                    } else if (task instanceof Epic) {
                        manager.epics.put(task.getId(), (Epic) task);
                    } else {
                        manager.tasks.put(task.getId(), task);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла", e);
        }
        return manager;
    }
}