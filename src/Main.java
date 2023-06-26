import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import task.Epic;
import task.Status;
import task.Subtask;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        Epic epic1 = new Epic("first", "firstEpicTask, testOfComaInDescription");
        Epic epic2 = new Epic("second", "secondEpicTask");
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);

        Subtask subtask1 = new Subtask("first", "firstSubtask",
                Status.NEW, epic1.getId());
        Subtask subtask2 = new Subtask("second", "secondSubtask",
                Status.NEW, epic1.getId());
        Subtask subtask3 = new Subtask("third", "thirdSubtask",
                Status.NEW, epic1.getId());
        taskManager.addSubtask(subtask1);
        taskManager.addSubtask(subtask2);
        taskManager.addSubtask(subtask3);

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