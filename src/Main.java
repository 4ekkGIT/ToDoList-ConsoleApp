import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File("tasks.txt");

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {

                    boolean done = line.startsWith("[✔]");
                    String desc = line.substring(4);

                    int dateIndex = desc.lastIndexOf(" (");
                    if (dateIndex != -1) {
                        desc = desc.substring(0, dateIndex);
                    }

                    Task task = new Task(desc);

                    if (done) {
                        task.markDone();
                    }

                    tasks.add(task);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            System.out.println("---- ToDo List ----");
            System.out.println("1. Show ALL task");
            System.out.println("2. ADD a task");
            System.out.println("3. Mark task as DONE");
            System.out.println("4. DELETE a task");
            System.out.println("5. Show COMPLETED tasks");
            System.out.println("6. Show PENDING tasks");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {

                    boolean inShowMenu = true;
                    while (inShowMenu) {
                        System.out.println("--- Your Tasks ---");
                        if (tasks.isEmpty()) {
                            System.out.println("There are no tasks yet!");
                        }
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        System.out.println("0. Back to main menu");
                        System.out.print("Choose an option: ");

                        int choiceBack = scanner.nextInt();
                        scanner.nextLine();

                        if (choiceBack == 0) {
                            inShowMenu = false;
                        } else {
                            System.out.println("Invalid option!");
                        }
                    }
                }
                case 2 -> {
                    boolean inAddMenu = true;
                    while (inAddMenu) {
                        System.out.print("Enter '0' to go back to menu\nEnter a task description: ");
                        String description = scanner.nextLine();
                        if (description.equals("0")) {
                            inAddMenu = false;
                        }
                        else {
                            tasks.add(new Task(description));
                            saveTasks(tasks);
                            System.out.println("Task added!");
                        }
                    }
                }
                case 3 -> {
                    if (tasks.isEmpty()) {
                        System.out.println("You have no tasks");
                        break;
                    }

                    boolean inMarkMenu = true;
                    while (inMarkMenu) {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        System.out.println("0. Back to main menu");
                        System.out.print("Choose an option: ");
                        int numdone = scanner.nextInt();

                        if (numdone == 0) {
                            inMarkMenu = false;
                        } else if (numdone >= 1 && numdone <= tasks.size()) {
                            tasks.get(numdone - 1).markDone();
                            saveTasks(tasks);
                            System.out.println("Task marked as DONE!");
                        } else {
                            System.out.println("Invalid number/option!");
                        }
                    }
                }
                case 4 -> {
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks to delete");
                        break;
                    }

                    boolean inDeleteMenu = true;
                    while (inDeleteMenu) {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                        System.out.println("0. Back to main menu");
                        System.out.print("Choose an option: ");
                        int numdelete = scanner.nextInt();

                        if (numdelete == 0) {
                            inDeleteMenu = false;
                        } else if (numdelete >= 1 && numdelete <= tasks.size()) {
                            tasks.remove(numdelete - 1);
                            saveTasks(tasks);
                            System.out.println("Task DELETED!");
                        } else {
                            System.out.println("Invalid task number/option.");
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Exiting...");
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter("tasks.txt"))) {
                        for (Task task : tasks) {
                            bw.write(task.toString());
                            bw.newLine();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                case 5 -> {
                    boolean inCompletedmenu = true;

                    while (inCompletedmenu) {

                        boolean hasCompleted = false;

                        for (Task task : tasks) {
                            if (task.isDone()) {
                                System.out.println(task);
                                hasCompleted = true;
                            }
                        }

                        if (!hasCompleted) {
                            System.out.println("There are no completed tasks.");
                        }

                        System.out.println("1. DELETE ALL COMPLETED TASKS");
                        System.out.println("0. Back to main menu");
                        System.out.print("Choose an option: ");
                        int option = scanner.nextInt();
                        scanner.nextLine();

                        if (option == 0) {
                            inCompletedmenu = false;
                        } else if (option == 1) {
                            System.out.print("Are you sure you want to delete ALL your completed tasks (y/n)?: ");
                            String inCompletedmenuoption = scanner.nextLine().trim().toLowerCase();

                            if (inCompletedmenuoption.equals("y")) {
                                tasks.removeIf(Task::isDone);
                                saveTasks(tasks);
                                System.out.println("All completed tasks deleted!");
                            } else if (inCompletedmenuoption.equals("n")) {
                                System.out.println("Canceling...");
                            } else {
                                System.out.println("Invalid input! Enter 'y' or 'n'.");
                            }
                        } else {
                            System.out.println("Invalid option!");
                        }
                    }
                }
                case 6 -> {
                    boolean inPendingMenu = true;

                    while (inPendingMenu) {

                        boolean hasCompleted = false;

                        for (Task task : tasks) {
                            if (!task.isDone()) {
                                System.out.println(task);
                                hasCompleted = true;
                            }
                        }

                        if (!hasCompleted) {
                            System.out.println("There are no pending tasks.");
                        }

                        System.out.println("0. Back to main menu");
                        System.out.print("Choose an option: ");

                        int option = scanner.nextInt();

                        if (option == 0) {
                            inPendingMenu = false;
                        }
                        else {
                            System.out.println("Invalid option!");
                        }
                    }
                }
                default -> System.out.println("Invalid option!");
            }
        }
    }
    private static void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}