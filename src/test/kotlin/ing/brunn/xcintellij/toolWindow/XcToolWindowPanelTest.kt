package ing.brunn.xcintellij.toolWindow

import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.ui.UIUtil

class XcToolWindowPanelTest : BasePlatformTestCase() {

    fun testToolWindowPanelComponentInitialization() {
        val panel = XcToolWindowPanel(project)

        // Allow background threads and EDT events triggered in panel init to complete
        PlatformTestUtil.dispatchAllEventsInIdeEventQueue()
        UIUtil.dispatchAllInvocationEvents()

        assertNotNull(panel.taskList)
    }

    fun testRefreshTasksHandlesFailureGracefully() {
        // Set invalid path to force error state in refreshTasks
        ing.brunn.xcintellij.services.XcSettingsState.instance.xcExecutablePath = "invalid_binary"

        val panel = XcToolWindowPanel(project)
        panel.refreshTasks()

        // Wait for pooled thread and EDT updates
        PlatformTestUtil.dispatchAllEventsInIdeEventQueue()
        UIUtil.dispatchAllInvocationEvents()

        // Verify taskList component exists and model contains results or empty state
        assertNotNull(panel.taskList.model)
    }
}