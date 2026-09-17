package ing.brunn.xcintellij.services

import com.intellij.execution.ui.RunContentManager
import com.intellij.openapi.util.Disposer
import com.intellij.testFramework.PlatformTestUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.ui.UIUtil

class XcTaskRunnerServiceTest : BasePlatformTestCase() {
    override fun tearDown() {
        try {
            // Dispose any console view editors created by RunContentExecutor
            val runContentManager = RunContentManager.getInstance(project)
            runContentManager.allDescriptors.forEach { descriptor ->
                Disposer.dispose(descriptor)
            }
        } finally {
            super.tearDown()
        }
    }

    fun testRunnerServiceInstantiation() {
        val runner = project.getService(XcTaskRunnerService::class.java)
        assertNotNull(runner)
    }

    fun testRunTaskDoesNotCrashOnMissingDirectory() {
        val runner = project.getService(XcTaskRunnerService::class.java)

        try {
            runner.runTask("nonexistent-task")
        } catch (e: Exception) {
            fail("runTask threw an unexpected exception: ${e.message}")
        } finally {
            PlatformTestUtil.dispatchAllEventsInIdeEventQueue()
            UIUtil.dispatchAllInvocationEvents()
        }
    }
}
