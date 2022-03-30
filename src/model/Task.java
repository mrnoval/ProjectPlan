package model;

public class Task {

    private int taskID;
    private String taskName;
    private String startDate;
    private String endDate;
    private String status;
    private String dependency;

    public Task() {}

    public Task(int taskID, String taskName, String startDate, String endDate, String status, String dependency) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.dependency = dependency;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }
}
