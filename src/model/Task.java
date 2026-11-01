package model;
import java.util.Objects;

public class Task{
    private int id;
    private String name;
    private String details;
    private TaskStatus status;

    public Task(String name, String details, TaskStatus status){
        this.id = id;
        this.name = name;
        this.details = details;
        this.status = TaskStatus.NEW;
    }

    public int getId(){return id;}
    public String getName(){return name;}
    public String getDetails(){return details;}
    public TaskStatus getStatus(){return status;}

    public void setId(int id) { this.id = id; }
    public void setName(String name){this.name = name;}
    public void setDetils(String details){this.details = details;}
    public void setStatus(TaskStatus status){this.status = status;}

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task zadachi = (Task) o;
        return id == zadachi.id;  
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return "Task{id = " + id + ", name = " + name + ", details = " + details + ", status = " + status + "}";
    }

}

