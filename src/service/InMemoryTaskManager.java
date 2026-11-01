package service;

import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private Map<Integer, Task> tasks = new HashMap<>();
    private Map<Integer, Subtask> subtasks = new HashMap<>();
    private Map<Integer, Epic> epics = new HashMap<>();

    private int countId = 1;
    private int generateId(){
        return countId++;
    }

    // Task
    @Override
    public List<Task> getListTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks(){
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id){
        return tasks.get(id);
    }

    @Override
    public void addTask(Task task){
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task){
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }  
    }

    @Override
    public void removeTask(int id){
        tasks.remove(id);
    }

    // Epic
    public List<Epic> getListEpic(){
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpics(){
        epics.clear();
        subtasks.clear();
    }

    public Epic getEpicById(int id){
        return epics.get(id);
    }

    public void createEpic(Epic epic){
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic);
    }

    public void updateEpic(Epic epic){
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
            updateEpicStatus(epic);
        }
    }

    public void removeEpic(int id){
        Epic epic = epics.get(id);
        if (epic != null){
            for (int idSub: epic.getIdSubtasks()){
                subtasks.remove(idSub);
            }
            epics.remove(id);
        }
    }

    @Override
    public List<Subtask> getSubtaskInEpic(int idEpic) {
        List<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(idEpic);
        if (epic != null) {
            for (int idSub : epic.getIdSubtasks()) {
                Subtask sub = subtasks.get(idSub);
                if (sub != null) {
                    result.add(sub);
                }
            }
        }
        return result;
    }

    // Subtask
    @Override
    public List<Subtask> getListSubtask() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllSubtasks(){
        subtasks.clear();
        for (Epic epic: epics.values()){
            List<Integer> subIds = new ArrayList<>(epic.getIdSubtasks());
            for (int idSub: subIds){
                epic.removeIdSubtask(idSub);
            }
        }
        for (Epic epic : epics.values()) {
            updateEpicStatus(epic);
        }
    }

    @Override
    public Subtask getSubtaskById(int id){
        return subtasks.get(id);
    } 

    @Override
    public void addSubtask(Subtask subtask){
        subtask.setId(generateId());
        subtasks.put(subtask.getId(), subtask);
        
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic != null) {
            epic.addIdSubtask(subtask.getId());
            updateEpicStatus(epic);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask){
        if(subtasks.containsKey(subtask.getId())){
            subtasks.put(subtask.getId(), subtask);
            
            Epic epic = epics.get(subtask.getIdEpic());
            if (epic != null) {
                updateEpicStatus(epic);
            }
        }
    }
    
    @Override
    public void removeSubtask(int id){
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getIdEpic());
            if (epic != null) {
                epic.removeIdSubtask(id);
                updateEpicStatus(epic);
            }
            subtasks.remove(id);
        }
    }

    private void updateEpicStatus(Epic epic) {
        List<Integer> subIds = epic.getIdSubtasks();
        
        if (subIds.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int id : subIds) {
            Subtask sub = subtasks.get(id);
            if (sub == null) continue;
            
            if (sub.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
            if (sub.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
        }

        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    

    
}