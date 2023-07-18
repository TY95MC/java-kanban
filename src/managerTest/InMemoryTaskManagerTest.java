package managerTest;

import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @BeforeEach
    void createInMemoryTaskManager() {
        manager = new InMemoryTaskManager();
    }
}