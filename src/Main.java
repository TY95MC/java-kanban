public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        System.out.println("Создание задач.");
        Task task1 = taskManager.createTask(new Task("first", "firstTask", "NEW"));
        Task task2 = taskManager.createTask(new Task("second", "secondTask", "NEW"));

        System.out.println("Создание больших задач.");
        EpicTask epicTask1 = taskManager.createEpicTask(new EpicTask("first", "firstEpicTask", "NEW"));
        EpicTask epicTask2 = taskManager.createEpicTask(new EpicTask("second", "secondEpicTask", "NEW"));
        System.out.println(taskManager.getAllEpicTask().toString());

        System.out.println("Создание подзадач.");
        SubTask subTask1 = taskManager.createSubTask(new SubTask("first", "firstSubTask", "NEW", epicTask1.getId()));
        SubTask subTask2 = taskManager.createSubTask(new SubTask("second", "secondSubTask", "NEW", epicTask1.getId()));
        SubTask subTask3 = taskManager.createSubTask(new SubTask("third", "thirdSubTask", "NEW", epicTask2.getId()));
        SubTask subTask4 = taskManager.createSubTask(new SubTask("fourth", "fourthSubTask", "NEW", epicTask2.getId()));

        System.out.println("Получение списков задач.");
        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());

        System.out.println("Получение списков подзадач по большим задачам.");
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("Обновление задачи. Проверка смены статуса.");
        epicTask1.setTaskName("epicTask1UPD1");
        epicTask2.setTaskName("epicTask2UPD1");

        taskManager.updateEpicTask(epicTask1);
        taskManager.updateEpicTask(epicTask2);

        task1.setTaskName("Task1UPD1");
        task1.setStatus("IN_PROGRESS");
        task2.setTaskName("Task2UPD1");
        task2.setStatus("IN_PROGRESS");

        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subTask1.setTaskName("SubTask1UPD1");
        subTask1.setStatus("IN_PROGRESS");
        subTask2.setTaskName("SubTask2UPD1");
        subTask2.setStatus("IN_PROGRESS");
        subTask3.setTaskName("SubTask3UPD1");
        subTask3.setStatus("IN_PROGRESS");
        subTask4.setTaskName("SubTask4UPD1");
        subTask4.setStatus("IN_PROGRESS");

        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);
        taskManager.updateSubTask(subTask4);

        System.out.println("Получение списков задач.");
        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println("Получение списков подзадач по большим задачам.");
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("Второе обновление задачи. Проверка смены статуса.");
        task2.setTaskName("Task2UPD2");
        task2.setStatus("IN_PROGRESS");
        taskManager.updateTask(task2);

        subTask1.setTaskName("SubTask1UPD2");
        subTask1.setStatus("DONE");
        subTask2.setTaskName("SubTask2UPD2");
        subTask2.setStatus("DONE");
        subTask3.setTaskName("SubTask3UPD2");
        subTask3.setStatus("DONE");
        subTask4.setTaskName("SubTask4UPD2");
        subTask4.setStatus("DONE");
        taskManager.updateSubTask(subTask1);
        taskManager.updateSubTask(subTask2);
        taskManager.updateSubTask(subTask3);
        taskManager.updateSubTask(subTask4);

        System.out.println("Получение списков задач.");
        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println("Получение списков подзадач по большим задачам.");
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("Получение задач по ID.");
        System.out.println(taskManager.getTaskById(task1.id).toString());
        System.out.println(taskManager.getEpicTaskById(epicTask2.id).toString());
        System.out.println(taskManager.getSubTaskById(subTask3.id).toString());

        System.out.println("Удаление задачи по ID.");
        taskManager.removeEpicTaskById(epicTask1.id);
        taskManager.removeTaskById(task1.id);
        taskManager.removeSubTaskById(subTask4.id);

        System.out.println("Получение списков задач.");
        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println("Получение списков подзадач по большим задачам.");
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        System.out.println("Удаление списка задач.");
        taskManager.deleteAllTask();
        taskManager.deleteAllEpicTask();
        taskManager.deleteAllSubTask();

        System.out.println("Получение списков задач.");
        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());
    }
}
