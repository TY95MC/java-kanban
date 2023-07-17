import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic1 = new Epic("first", "firstEpicTask, testOfComaInDescription");
        Epic epic2 = new Epic("second", "secondEpicTask");
        epic1.setStartTime(LocalDateTime.now().minus(Duration.ofMinutes(30)));
        epic2.setStartTime(LocalDateTime.now().minus(Duration.ofMinutes(15)));
        epic1.setDuration(Duration.ofMinutes(5));
        epic2.setDuration(Duration.ofMinutes(5));
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Task task1 = new Task("first", "firstTask", Status.NEW);
        Task task2 = new Task("second", "secondTask", Status.NEW);
        task2.setStartTime(LocalDateTime.now());
        task1.setStartTime(LocalDateTime.now().plus(Duration.ofMinutes(20)));
        task2.setDuration(Duration.ofMinutes(15));
        task1.setDuration(Duration.ofMinutes(15));
        System.out.println(task1.getStartTime());
        System.out.println(task2.getStartTime());
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        Subtask subtask1 = new Subtask("first", "firstSubtask",
                Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("second", "secondSubtask",
                Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("third", "thirdSubtask",
                Status.NEW, epic1.getId());
        subtask2.setStartTime(LocalDateTime.now().plus(Duration.ofMinutes(40)));
        subtask2.setDuration(Duration.ofMinutes(15));
        subtask3.setStartTime(LocalDateTime.now().plus(Duration.ofMinutes(60)));
        subtask3.setDuration(Duration.ofMinutes(25));

        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);
        System.out.println("\nПроверка подсчета времени эпика.\n" + LocalTime.of(0,0).plus(epic1.getDuration())
                .format(DateTimeFormatter.ofPattern("HH:mm")));

        System.out.println("\nПроверка работы менеджера истории. Шаг 1.\n");

        taskManager.getTask(task1.getId());
        taskManager.getTask(task1.getId());
        taskManager.getTask(task2.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getSubtask(subtask2.getId());
        taskManager.getSubtask(subtask1.getId());
        taskManager.getSubtask(subtask3.getId());
        taskManager.getTask(task1.getId());
        System.out.println(taskManager.getHistory());
        System.out.println("\nВызов списка задач по приоритету.\n");
        System.out.println(taskManager.getPrioritizedTasks());


       System.out.println("\nПроверка работы менеджера истории. Шаг 2.\n");
        taskManager.deleteSubtask(subtask3.getId());
        System.out.println(taskManager.getHistory());

        System.out.println("\nПроверка работы менеджера истории. Шаг 3.\n");
        taskManager.deleteTask(task1.getId());
        System.out.println(taskManager.getHistory());

        System.out.println("\nПроверка работы менеджера истории. Шаг 4.\n");
        taskManager.deleteTask(task2.getId());
        System.out.println(taskManager.getHistory());
        System.out.println("\nВторой вызов списка задач по приоритету.\n");
        System.out.println(taskManager.getPrioritizedTasks());
    }

}