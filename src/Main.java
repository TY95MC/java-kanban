
public class Main {

    public static void main(String[] args) {

        TaskManager taskManager1 = Managers.getDefault();
        TaskManager taskManager2 = Managers.getDefault();

        System.out.println("\nСоздание задач.\n");
        Task task1 = taskManager1.createTask(new Task("first", "firstTask", Status.NEW));
        Task task2 = taskManager2.createTask(new Task("second", "secondTask", Status.NEW));

        System.out.println("\nСоздание больших задач.\n");
        EpicTask epicTask1 = taskManager1.createEpicTask(new EpicTask("first",
                "firstEpicTask", Status.NEW));
        EpicTask epicTask2 = taskManager2.createEpicTask(new EpicTask("second",
                "secondEpicTask", Status.NEW));

        System.out.println("\nСоздание подзадач.\n");
        SubTask subTask1 = taskManager1.createSubTask(new SubTask("first", "firstSubTask",
                Status.NEW, epicTask1.getId()));
        SubTask subTask2 = taskManager1.createSubTask(new SubTask("second", "secondSubTask",
                Status.NEW, epicTask1.getId()));
        SubTask subTask3 = taskManager2.createSubTask(new SubTask("third", "thirdSubTask",
                Status.NEW, epicTask2.getId()));
        SubTask subTask4 = taskManager2.createSubTask(new SubTask("fourth", "fourthSubTask",
                Status.NEW, epicTask2.getId()));

        System.out.println("\nПроверка работы менеджера истории.\n");

        taskManager1.getTaskById(task1.id);
        taskManager1.getEpicTaskById(epicTask1.id);
        taskManager1.getSubTaskById(subTask1.id);
        taskManager1.getSubTaskById(subTask2.id);

        taskManager2.getTaskById(task2.id);
        taskManager2.getEpicTaskById(epicTask2.id);
        taskManager2.getSubTaskById(subTask3.id);
        taskManager2.getSubTaskById(subTask4.id);

        System.out.println(taskManager1.getHistory());
        System.out.println(taskManager2.getHistory());

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getAllTask().toString());
        System.out.println(taskManager2.getAllTask().toString());
        System.out.println(taskManager1.getAllEpicTask().toString());
        System.out.println(taskManager2.getAllEpicTask().toString());
        System.out.println(taskManager1.getAllSubTask().toString());
        System.out.println(taskManager2.getAllSubTask().toString());

        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager2.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("\nОбновление задачи. Проверка смены статуса.\n");
        epicTask1.setTaskName("epicTask1UPD1");
        epicTask2.setTaskName("epicTask2UPD1");

        taskManager1.updateEpicTask(epicTask1);
        taskManager2.updateEpicTask(epicTask2);

        task1.setTaskName("Task1UPD1");
        task1.setStatus(Status.IN_PROGRESS);
        task2.setTaskName("Task2UPD1");
        task2.setStatus(Status.IN_PROGRESS);

        taskManager1.updateTask(task1);
        taskManager2.updateTask(task2);

        subTask1.setTaskName("SubTask1UPD1");
        subTask1.setStatus(Status.IN_PROGRESS);
        subTask2.setTaskName("SubTask2UPD1");
        subTask2.setStatus(Status.IN_PROGRESS);
        subTask3.setTaskName("SubTask3UPD1");
        subTask3.setStatus(Status.IN_PROGRESS);
        subTask4.setTaskName("SubTask4UPD1");
        subTask4.setStatus(Status.IN_PROGRESS);

        taskManager1.updateSubTask(subTask1);
        taskManager1.updateSubTask(subTask2);
        taskManager2.updateSubTask(subTask3);
        taskManager2.updateSubTask(subTask4);

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getAllTask().toString());
        System.out.println(taskManager2.getAllTask().toString());
        System.out.println(taskManager1.getAllEpicTask().toString());
        System.out.println(taskManager2.getAllEpicTask().toString());
        System.out.println(taskManager1.getAllSubTask().toString());
        System.out.println(taskManager2.getAllSubTask().toString());
        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager2.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("\nВторое обновление задачи. Проверка смены статуса.\n");
        task2.setTaskName("Task2UPD2");
        task2.setStatus(Status.IN_PROGRESS);
        taskManager1.updateTask(task2);

        subTask1.setTaskName("SubTask1UPD2");
        subTask1.setStatus(Status.DONE);
        subTask2.setTaskName("SubTask2UPD2");
        subTask2.setStatus(Status.DONE);
        subTask3.setTaskName("SubTask3UPD2");
        subTask3.setStatus(Status.DONE);
        subTask4.setTaskName("SubTask4UPD2");
        subTask4.setStatus(Status.DONE);
        taskManager1.updateSubTask(subTask1);
        taskManager1.updateSubTask(subTask2);
        taskManager2.updateSubTask(subTask3);
        taskManager2.updateSubTask(subTask4);

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getAllTask().toString());
        System.out.println(taskManager2.getAllTask().toString());
        System.out.println(taskManager1.getAllEpicTask().toString());
        System.out.println(taskManager2.getAllEpicTask().toString());
        System.out.println(taskManager1.getAllSubTask().toString());
        System.out.println(taskManager2.getAllSubTask().toString());
        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager2.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("\nПолучение задач по ID.\n");
        System.out.println(taskManager1.getTaskById(task1.id).toString());
        System.out.println(taskManager2.getEpicTaskById(epicTask2.id).toString());
        System.out.println(taskManager2.getSubTaskById(subTask3.id).toString());

        System.out.println("\nУдаление задачи по ID.\n");
        taskManager1.removeEpicTaskById(epicTask1.id);
        taskManager1.removeTaskById(task1.id);
        taskManager2.removeSubTaskById(subTask4.id);

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getAllTask().toString());
        System.out.println(taskManager2.getAllTask().toString());
        System.out.println(taskManager1.getAllEpicTask().toString());
        System.out.println(taskManager2.getAllEpicTask().toString());
        System.out.println(taskManager1.getAllSubTask().toString());
        System.out.println(taskManager2.getAllSubTask().toString());
        System.out.println("\nПолучение списков подзадач по большим задачам.\n");
        System.out.println(taskManager1.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager1.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("\nУдаление списка задач.\n");
        taskManager1.deleteAllTask();
        taskManager2.deleteAllTask();
        taskManager1.deleteAllEpicTask();
        taskManager2.deleteAllEpicTask();
        taskManager1.deleteAllSubTask();
        taskManager2.deleteAllSubTask();

        System.out.println("\nПолучение списков задач.\n");
        System.out.println(taskManager1.getAllTask().toString());
        System.out.println(taskManager2.getAllTask().toString());
        System.out.println(taskManager1.getAllEpicTask().toString());
        System.out.println(taskManager2.getAllEpicTask().toString());
        System.out.println(taskManager1.getAllSubTask().toString());
        System.out.println(taskManager2.getAllSubTask().toString());
        System.out.println(taskManager1.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager2.getSubTasksByEpicTask(epicTask2).toString());
    }

}
