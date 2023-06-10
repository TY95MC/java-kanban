import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic1 = taskManager.addEpic(new Epic("first", "firstEpicTask"));
        Epic epic2 = taskManager.addEpic(new Epic("second", "secondEpicTask"));

        Subtask subtask1 = taskManager.addSubtask(new Subtask("first", "firstSubtask",
                Status.NEW, epic1.getId()));
        Subtask subtask2 = taskManager.addSubtask(new Subtask("second", "secondSubtask",
                Status.NEW, epic1.getId()));
        Subtask subtask3 = taskManager.addSubtask(new Subtask("third", "thirdSubtask",
                Status.NEW, epic1.getId()));

        System.out.println("\nПроверка работы менеджера истории. Шаг 1.\n");

        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic1.getId());
        taskManager.getEpic(epic2.getId());
        taskManager.getEpic(350);
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
        taskManager.getEpic(epic1.getId());
        System.out.println(taskManager.getHistory());

        System.out.println("\nПроверка работы менеджера истории. Шаг 2.\n");
        taskManager.deleteSubtask(subtask3.getId());
        System.out.println(taskManager.getHistory());

        System.out.println("\nПроверка работы менеджера истории. Шаг 3.\n");
        taskManager.deleteEpic(epic1.getId());
        System.out.println(taskManager.getHistory());

        System.out.println("\nПроверка работы менеджера истории. Шаг 4.\n");
        taskManager.getEpic(epic1.getId());
        taskManager.deleteEpic(epic2.getId());
        taskManager.getEpic(epic2.getId());
        System.out.println(taskManager.getHistory());
    }

}

/* Тест работоспособности программы к 4 спринту*/

/*TaskManager taskManager1 = Managers.getDefault();
        TaskManager taskManager2 = Managers.getDefault();

        System.out.println("\nСоздание задач.\n");
        Task task1 = taskManager1.addTask(new Task("first", "firstTask", Status.NEW));
        Task task2 = taskManager2.addTask(new Task("second", "secondTask", Status.NEW));

        System.out.println("\nСоздание больших задач.\n");
        Epic epic1 = taskManager1.addEpic(new Epic("first", "firstEpicTask"));
        Epic epic2 = taskManager2.addEpic(new Epic("second", "secondEpicTask"));

        System.out.println("\nСоздание подзадач.\n");
        Subtask subtask1 = taskManager1.addSubtask(new Subtask("first", "firstSubtask",
                Status.NEW, epic1.getId()));
        Subtask subtask2 = taskManager1.addSubtask(new Subtask("second", "secondSubtask",
                Status.NEW, epic1.getId()));
        Subtask subtask3 = taskManager2.addSubtask(new Subtask("third", "thirdSubtask",
                Status.NEW, epic2.getId()));
        Subtask subtask4 = taskManager2.addSubtask(new Subtask("fourth", "fourthSubtask",
                Status.NEW, epic2.getId()));

        System.out.println("\nПроверка работы менеджера истории.\n");

        taskManager1.getTask(task1.getId());
        taskManager1.getEpic(epic1.getId());
        taskManager1.getSubtask(subtask1.getId());
        taskManager1.getSubtask(subtask2.getId());

        taskManager2.getTask(task2.getId());
        taskManager2.getEpic(epic2.getId());
        taskManager2.getSubtask(subtask3.getId());
        taskManager2.getSubtask(subtask4.getId());

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
        epic1.setName("epic1UPD1");
        epic2.setName("epic2UPD1");

        taskManager1.updateEpic(epic1);
        taskManager2.updateEpic(epic2);

        task1.setName("Task1UPD1");
        task1.setStatus(Status.IN_PROGRESS);
        task2.setName("Task2UPD1");
        task2.setStatus(Status.IN_PROGRESS);

        taskManager1.updateTask(task1);
        taskManager2.updateTask(task2);

        subtask1.setName("Subtask1UPD1");
        subtask1.setStatus(Status.IN_PROGRESS);
        subtask2.setName("Subtask2UPD1");
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setName("Subtask3UPD1");
        subtask3.setStatus(Status.IN_PROGRESS);
        subtask4.setName("Subtask4UPD1");
        subtask4.setStatus(Status.IN_PROGRESS);

        taskManager1.updateSubtask(subtask1);
        taskManager1.updateSubtask(subtask2);
        taskManager2.updateSubtask(subtask3);
        taskManager2.updateSubtask(subtask4);

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
        task2.setName("Task2UPD2");
        task2.setStatus(Status.IN_PROGRESS);
        taskManager1.updateTask(task2);

        subtask1.setName("Subtask1UPD2");
        subtask1.setStatus(Status.DONE);
        subtask2.setName("Subtask2UPD2");
        subtask2.setStatus(Status.DONE);
        subtask3.setName("Subtask3UPD2");
        subtask3.setStatus(Status.DONE);
        subtask4.setName("Subtask4UPD2");
        subtask4.setStatus(Status.DONE);
        taskManager1.updateSubtask(subtask1);
        taskManager1.updateSubtask(subtask2);
        taskManager2.updateSubtask(subtask3);
        taskManager2.updateSubtask(subtask4);

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
        System.out.println(taskManager2.getSubtask(subtask3.getId()).toString());

        System.out.println("\nУдаление задачи по ID.\n");
        taskManager1.deleteEpic(epic1.getId());
        taskManager1.deleteTask(task1.getId());
        taskManager2.deleteSubtask(subtask4.getId());

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
        System.out.println(taskManager2.getEpicSubtasks(epic2.getId()).toString());*/