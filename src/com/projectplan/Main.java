package com.projectplan;

import model.Task;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Constants
    private static final String COMMA = ",";

    private static List taskList = new ArrayList<Task>();
    private static List dependecyTaskList = new ArrayList<String>();

    public static void main(String[] args) {
        System.out.println("Project Plans");
        setDBData();
        Scanner sc = new Scanner(System.in);
        boolean systemRunning = true;

        while (systemRunning) {
            System.out.println("Select what to do: ");
            System.out.println("1. Show all tasks/schedule");
            System.out.println("2. Add tasks");
            System.out.println("3. Change status of Task");
            System.out.println("4. Show current Tasks");
            System.out.println("5. Exit");
            String userInput = sc.nextLine();

            switch (userInput) {
                case "1":
                    showSchedule();
                    break;
                case "2":
                    addTask();
                    break;
                case "3":
                    changeTaskStatus();
                    break;
                case "4":
                    showCurrentTasks();
                    break;
                case "5":
                    System.out.println("System Exiting!!!");
                    systemRunning = false;
                    break;
                default:
                    System.out.println("Please select 1, 2, or 3");
                    break;
            }
        }
    }

    /**
     * Shows the current schedule
     * @params
     * @return
     * */
    private static void showSchedule () {
        System.out.println("-------------------------------------------\n");
        if (taskList.size() != 0) {
            for (Object o : taskList) {
                Task task = (Task) o;
                System.out.print("Task ID: " + task.getTaskID());
                System.out.print(" Task Name: " + task.getTaskName());
                System.out.print(" Start Date: " + task.getStartDate());
                System.out.print(" End Date: " + task.getEndDate());
                System.out.print(" Status: " + task.getStatus());
                System.out.print(" Dependency: " + task.getDependency() + "\n");
            }
        }
        System.out.println("-------------------------------------------\n");
        pressAnyKeyToContinue();
    }

    /**
     * Adds a new task in the Task Bucket
     * @params
     * @return
     */
    private static void addTask () {
        Scanner sc = new Scanner(System.in);

        System.out.println("-------------------------------------------\n");
        System.out.println("Adding a new task");
        System.out.println("Please input the following: ");
        System.out.print("Task Name: ");
        String taskName = sc.nextLine();
        System.out.print("Start Date: ");
        String startDate = sc.nextLine();
        System.out.print("End Date: ");
        String endDate = sc.nextLine();
        System.out.print("Dependency (Task ID)\n For multiple dependencies please separate with comma");
        String dependency = sc.nextLine();

        Task task = new Task(taskList.size() + 1, taskName, startDate, endDate, "Not Started", dependency);
        taskList.add(task);
        System.out.println("Added successfully!");
        System.out.println("-------------------------------------------\n");
        pressAnyKeyToContinue();
    }

    /**
     * Changes the status of a Task
     * @param
     * @return
     * */
    private static void changeTaskStatus () {
        System.out.println("-------------------------------------------\n");
        System.out.println("Change Status of Task: ");

        Scanner sc = new Scanner(System.in);

        System.out.print("Please select Task ID: ");
        String selectedTaskID = sc.nextLine();
        int listIndex = 0;

        for (Object o : taskList) {
            Task task = (Task) o;
            if (task.getTaskID() == Integer.valueOf(selectedTaskID)) {
                System.out.println("Task exists");
                System.out.print("New Status: ");
                String statusChange = sc.nextLine();

                if (task.getStatus().equals("Not Started") && dependecyCheck(task)) {
                    System.out.println("UNABLE TO UPDATE");
                    System.out.println("Please finish Task dependency!!!");

                    for (Object dependencyTaskID : dependecyTaskList) {
                        System.out.println("TASK ID: " + dependencyTaskID);
                    }
                    pressAnyKeyToContinue();
                } else {
                    task.setStatus(statusChange);
                    taskList.set(listIndex, task);
                }

            }
            listIndex++;
        }
        System.out.println("-------------------------------------------\n");
    }

    /**
     * Checks if task has dependency
     * @param task Task
     * @return boolean depency
     */
    private static boolean dependecyCheck (Task task) {
        boolean dependency = false;

        if (task.getDependency().equals("")) {
            return dependency;
        } else {
            for (Object o : taskList) {
                Task taskO = (Task) o;
                String[] dependencies = task.getDependency().split(COMMA);

                for (String dependencyStr : dependencies) {
                    if (dependencyStr.equals(String.valueOf(taskO.getTaskID()))
                            && !taskO.getStatus().equals("Completed")
                    ) {
                        dependecyTaskList.add(dependencyStr);
                        dependency = true;
                    }
                }
            }
        }

        return dependency;
    }

    /**
     * Show current tasks
     */
    private static void showCurrentTasks () {
        System.out.println("-------------------------------------------\n");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(currentDate));

        if (taskList.size() != 0) {
            for (Object o : taskList) {
                Task task = (Task) o;
                Date startDate = Date.valueOf(task.getStartDate());
                Date endDate = Date.valueOf(task.getEndDate());

                if (currentDate.before(startDate)
                        && currentDate.after(endDate)) {
                    System.out.print("Task ID: " + task.getTaskID());
                    System.out.print(" Task Name: " + task.getTaskName());
                    System.out.print(" Start Date: " + task.getStartDate());
                    System.out.print(" End Date: " + task.getEndDate());
                    System.out.print(" Status: " + task.getStatus());
                    System.out.print(" Dependency: " + task.getDependency() + "\n");
                }
            }
        }
        System.out.println("-------------------------------------------\n");
        pressAnyKeyToContinue();
    }

    /**
     * Temporary DB
     */
    private static void setDBData() {
        Task task = new Task(1, "Task1", "03/29/2022", "03/31/2022", "Completed", "");
        Task task1 = new Task(2, "Task2", "03/29/2022", "03/30/2022", "Not Started", "");
        Task task2 = new Task(3, "Task3", "03/29/2022", "03/30/2022", "Blocked", "1");
        Task task3 = new Task(4, "Task4", "03/29/2022", "03/30/2022", "Not Started", "1,3,2");
        Task task4 = new Task(5, "Task5", "03/29/2022", "03/30/2022", "Not Started", "2");

        taskList.add(task);
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);
    }

    /**
     * Console accessory to stop proceeding until Enter key is pressed
     */
    private static void pressAnyKeyToContinue()
    {
        System.out.println("\nPress Enter key to continue...");
        try {
            System.in.read();
        }
        catch(Exception e) {}
    }
}
