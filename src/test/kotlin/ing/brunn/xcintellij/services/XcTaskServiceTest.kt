package ing.brunn.xcintellij.services

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class XcTaskServiceTest : BasePlatformTestCase() {
    fun testTaskServiceInstantiation() {
        val service = project.getService(XcTaskService::class.java)
        assertNotNull(service)
    }

    fun testFetchTasksGracefullyHandlesInvalidExecutable() {
        // Point to an invalid path
        XcSettingsState.instance.xcExecutablePath = "/invalid/path/to/nonexistent_xc"

        val service = project.getService(XcTaskService::class.java)
        val tasks = service.fetchTasks()

        assertTrue(tasks.isEmpty())
    }
}
