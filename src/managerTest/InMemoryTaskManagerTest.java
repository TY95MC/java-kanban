package managerTest;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    void createInMemoryTaskManager() {
        manager = new InMemoryTaskManager();
    }
}