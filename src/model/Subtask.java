package model;

public class Subtask extends Task{
    private int idEpic;
     
    public Subtask(String name, String details, TaskStatus status, int idEpic){
        super(name, details, status);
        this.idEpic = idEpic;
    }

    public int getIdEpic(){return idEpic;}
    public void setEpicId(int idEpic){this.idEpic = idEpic;}

    @Override
    public String toString(){
        return "Subtask{id = " + getId() + ", name = " + getName() + ", details = " + getDetails() + ", status = " + getStatus() + ", IdEpic = " + idEpic + "}";
    }
}