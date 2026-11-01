package model;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    List<Integer> idSubtasks = new ArrayList<>();

    public Epic(String name, String details){
        super(name, details, TaskStatus.NEW);
    }

    public List<Integer> getIdSubtasks(){return new ArrayList<>(idSubtasks);}
    
    public void addIdSubtask(int idSub){
        idSubtasks.add(idSub);
    }

    public void removeIdSubtask(int idSub){
        idSubtasks.remove((Integer) idSub);
    }

    @Override
    public String toString(){
        return "Subtask{id = " + getId() + ", name = " + getName() + ", details = " + getDetails() + ", status = " + getStatus() + ", idSubtasks = " + idSubtasks + "}";
    }
   
}