public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("first", "firstTask");
        Task task2 = new Task("second", "secondTask");

        EpicTask epicTask1 = new EpicTask("first", "firstEpicTask");
        EpicTask epicTask2 = new EpicTask("second", "secondEpicTask");

        SubTask subTask1 = new SubTask("first", "firstSubTask");
        SubTask subTask2 = new SubTask("second", "secondSubTask");
        SubTask subTask3 = new SubTask("third", "thirdSubTask");
        SubTask subTask4 = new SubTask("fourth", "fourthSubTask");

        //Создание задач
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.createEpicTask(epicTask1);
        taskManager.createEpicTask(epicTask2);

        taskManager.createSubTask(epicTask1, subTask1);
        taskManager.createSubTask(epicTask1, subTask2);
        taskManager.createSubTask(epicTask2, subTask3);
        taskManager.createSubTask(epicTask2, subTask4);

        //Получение списков задач
        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());

        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        //Обновление задач для проверки смены статуса
        epicTask1.setTaskName("epicTask1UPD1");
        epicTask2.setTaskName("epicTask2UPD1");

        taskManager.updateEpicTask(epicTask1);
        taskManager.updateEpicTask(epicTask2);

        task1.setTaskName("Task1UPD1");
        task2.setTaskName("Task2UPD1");

        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subTask1.setTaskName("SubTask1UPD1");
        subTask2.setTaskName("SubTask2UPD1");
        subTask3.setTaskName("SubTask3UPD1");
        subTask4.setTaskName("SubTask4UPD1");

        taskManager.updateSubTask(epicTask1, subTask1);
        taskManager.updateSubTask(epicTask1, subTask2);
        taskManager.updateSubTask(epicTask2, subTask3);
        taskManager.updateSubTask(epicTask2, subTask4);

        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        //Второе обновление задач для проверки смены статуса
        task2.setTaskName("Task2UPD2");
        taskManager.updateTask(task2);

        subTask1.setTaskName("SubTask1UPD2");
        subTask2.setTaskName("SubTask2UPD2");
        subTask3.setTaskName("SubTask3UPD2");
        subTask4.setTaskName("SubTask4UPD2");
        taskManager.updateSubTask(epicTask1, subTask1);
        taskManager.updateSubTask(epicTask1, subTask2);
        taskManager.updateSubTask(epicTask2, subTask3);
        taskManager.updateSubTask(epicTask2, subTask4);

        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        //Получение задач по ID
        System.out.println(taskManager.getTaskById(task1.id).toString());
        System.out.println(taskManager.getEpicTaskById(epicTask2.id).toString());
        System.out.println(taskManager.getSubTaskById(subTask3.id).toString());

        //Удаление задач по ID
        taskManager.removeEpicTaskById(epicTask1.id);
        taskManager.removeTaskById(task1.id);
        taskManager.removeSubTaskById(subTask4.id);

        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());

        //Полное удаление задач
        taskManager.deleteAllTask();
        taskManager.deleteAllEpicTask();
        taskManager.deleteAllSubTask();

        System.out.println(taskManager.getAllTask().toString());
        System.out.println(taskManager.getAllEpicTask().toString());
        System.out.println(taskManager.getAllSubTask().toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask1).toString());
        System.out.println(taskManager.getSubTasksByEpicTask(epicTask2).toString());
    }
}
