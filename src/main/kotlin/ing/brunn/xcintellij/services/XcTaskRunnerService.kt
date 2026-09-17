package ing.brunn.xcintellij.services

import com.intellij.execution.RunContentExecutor
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import java.io.File
import com.intellij.openapi.diagnostic.thisLogger

@Service(Service.Level.PROJECT)
class XcTaskRunnerService(private val project: Project) {

    @JvmOverloads
    fun runTask(taskName: String, parentDisposable: Disposable = project) {
        val basePath = project.basePath ?: return
        val workDir = File(basePath)
        if (!workDir.exists()) return

        val xcExecutable = XcSettingsState.instance.xcExecutablePath

        try {
            val commandLine = GeneralCommandLine(xcExecutable, taskName)
                .withWorkDirectory(workDir)

            val processHandler = ProcessHandlerFactory.getInstance()
                .createColoredProcessHandler(commandLine)

            ProcessTerminatedListener.attach(processHandler)

            RunContentExecutor(project, processHandler)
                .withTitle("xc: $taskName")
                .withActivateToolWindow(true)
                .run()
        } catch (e: Exception) {
            thisLogger().warn("Failed to execute xc task '$taskName' with executable '$xcExecutable'", e)
        }
    }
}