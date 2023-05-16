import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager1 = Managers.getDefault();
        TaskManager taskManager2 = Managers.getDefault();

        System.out.println("\nСоздание задач.\n");
        Task task1 = taskManager1.addTask(new Task("first", "firstTask", Status.NEW));
        Task task2 = taskManager2.addTask(new Task("second", "secondTask", Status.NEW));

        System.out.println("\nСоздание больших задач.\n");
        Epic epic1 = taskManager1.addEpic(new Epic("first", "firstEpicTask"));
        Epic epic2 = taskManager2.addEpic(new Epic("second", "secondEpicTask"));

        System.out.println("\nСоздание подзадач.\n");
        Subtask Subtask1 = taskManager1.addSubtask(new Subtask("first", "firstSubtask",
                Status.NEW, epic1.getId()));
        Subtask Subtask2 = taskManager1.addSubtask(new Subtask("second", "secondSubtask",
                Status.NEW, epic1.getId()));
        Subtask Subtask3 = taskManager2.addSubtask(new Subtask("third", "thirdSubtask",
                Status.NEW, epic2.getId()));
        Subtask Subtask4 = taskManager2.addSubtask(new Subtask("fourth", "fourthSubtask",
                Status.NEW, epic2.getId()));

        System.out.println("\nПроверка работы менеджера истории.\n");

        taskManager1.getTask(task1.getId());
        taskManager1.getEpic(epic1.getId());
        taskManager1.getSubtask(Subtask1.getId());
        taskManager1.getSubtask(Subtask2.getId());

        taskManager2.getTask(task2.getId());
        taskManager2.getEpic(epic2.getId());
        taskManager2.getSubtask(Subtask3.getId());
        taskManager2.getSubtask(Subtask4.getId());

        System.out.println(taskManager1.getHistory());
        System.out.println(taskManager2.getHistory());

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getTasks().toString());
        System.out.println(taskManager2.getTasks().toString());
        System.out.println(taskManager1.getEpics().toString());
        System.out.println(taskManager2.getEpics().toString());
        System.out.println(taskManager1.getSubtasks().toString());
        System.out.println(taskManager2.getSubtasks().toString());

        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getEpicSubtasks(epic1.getId()).toString());
        System.out.println(taskManager2.getEpicSubtasks(epic2.getId()).toString());

        System.out.println("\nОбновление задачи. Проверка смены статуса.\n");
        epic1.setTaskName("epic1UPD1");
        epic2.setTaskName("epic2UPD1");

        taskManager1.updateEpic(epic1);
        taskManager2.updateEpic(epic2);

        task1.setTaskName("Task1UPD1");
        task1.setStatus(Status.IN_PROGRESS);
        task2.setTaskName("Task2UPD1");
        task2.setStatus(Status.IN_PROGRESS);

        taskManager1.updateTask(task1);
        taskManager2.updateTask(task2);

        Subtask1.setTaskName("Subtask1UPD1");
        Subtask1.setStatus(Status.IN_PROGRESS);
        Subtask2.setTaskName("Subtask2UPD1");
        Subtask2.setStatus(Status.IN_PROGRESS);
        Subtask3.setTaskName("Subtask3UPD1");
        Subtask3.setStatus(Status.IN_PROGRESS);
        Subtask4.setTaskName("Subtask4UPD1");
        Subtask4.setStatus(Status.IN_PROGRESS);

        taskManager1.updateSubtask(Subtask1);
        taskManager1.updateSubtask(Subtask2);
        taskManager2.updateSubtask(Subtask3);
        taskManager2.updateSubtask(Subtask4);

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getTasks().toString());
        System.out.println(taskManager2.getTasks().toString());
        System.out.println(taskManager1.getEpics().toString());
        System.out.println(taskManager2.getEpics().toString());
        System.out.println(taskManager1.getSubtasks().toString());
        System.out.println(taskManager2.getSubtasks().toString());
        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getEpicSubtasks(epic1.getId()).toString());
        System.out.println(taskManager2.getEpicSubtasks(epic2.getId()).toString());

        System.out.println("\nВторое обновление задачи. Проверка смены статуса.\n");
        task2.setTaskName("Task2UPD2");
        task2.setStatus(Status.IN_PROGRESS);
        taskManager1.updateTask(task2);

        Subtask1.setTaskName("Subtask1UPD2");
        Subtask1.setStatus(Status.DONE);
        Subtask2.setTaskName("Subtask2UPD2");
        Subtask2.setStatus(Status.DONE);
        Subtask3.setTaskName("Subtask3UPD2");
        Subtask3.setStatus(Status.DONE);
        Subtask4.setTaskName("Subtask4UPD2");
        Subtask4.setStatus(Status.DONE);
        taskManager1.updateSubtask(Subtask1);
        taskManager1.updateSubtask(Subtask2);
        taskManager2.updateSubtask(Subtask3);
        taskManager2.updateSubtask(Subtask4);

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getTasks().toString());
        System.out.println(taskManager2.getTasks().toString());
        System.out.println(taskManager1.getEpics().toString());
        System.out.println(taskManager2.getEpics().toString());
        System.out.println(taskManager1.getSubtasks().toString());
        System.out.println(taskManager2.getSubtasks().toString());
        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getEpicSubtasks(epic1.getId()).toString());
        System.out.println(taskManager2.getEpicSubtasks(epic2.getId()).toString());

        System.out.println("\nПолучение задач по ID.\n");
        System.out.println(taskManager1.getTask(task1.getId()).toString());
        System.out.println(taskManager2.getEpic(epic2.getId()).toString());
        System.out.println(taskManager2.getSubtask(Subtask3.getId()).toString());

        System.out.println("\nУдаление задачи по ID.\n");
        taskManager1.removeEpicTaskById(epic1.getId());
        taskManager1.removeTaskById(task1.getId());
        taskManager2.removeSubtaskById(Subtask4.getId());

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getTasks().toString());
        System.out.println(taskManager2.getTasks().toString());
        System.out.println(taskManager1.getEpics().toString());
        System.out.println(taskManager2.getEpics().toString());
        System.out.println(taskManager1.getSubtasks().toString());
        System.out.println(taskManager2.getSubtasks().toString());
        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getEpicSubtasks(epic1.getId()).toString());
        System.out.println(taskManager1.getEpicSubtasks(epic2.getId()).toString());

        System.out.println("\nУдаление списка задач.\n");
        taskManager1.deleteTasks();
        taskManager2.deleteTasks();
        taskManager1.deleteEpics();
        taskManager2.deleteEpics();
        taskManager1.deleteSubtasks();
        taskManager2.deleteSubtasks();

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getTasks().toString());
        System.out.println(taskManager2.getTasks().toString());
        System.out.println(taskManager1.getEpics().toString());
        System.out.println(taskManager2.getEpics().toString());
        System.out.println(taskManager1.getSubtasks().toString());
        System.out.println(taskManager2.getSubtasks().toString());
        System.out.println(taskManager1.getEpicSubtasks(epic1.getId()).toString());
        System.out.println(taskManager2.getEpicSubtasks(epic2.getId()).toString());
    }

}
