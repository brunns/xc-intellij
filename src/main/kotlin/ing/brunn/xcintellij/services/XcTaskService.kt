package ing.brunn.xcintellij.services

import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.CapturingProcessHandler
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import java.io.File

@Service(Service.Level.PROJECT)
class XcTaskService(private val project: Project) {
    fun fetchTasks(): List<String> {
        val basePath = project.basePath ?: return emptyList()
        val xcExecutable = XcSettingsState.instance.xcExecutablePath

        val commandLine =
            GeneralCommandLine(xcExecutable, "-short")
                .withWorkDirectory(File(basePath))

        return try {
            val handler = CapturingProcessHandler(commandLine)
            val output = handler.runProcess(3000) // 3 second timeout
            if (output.exitCode == 0) {
                output.stdoutLines.filter { it.isNotBlank() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
