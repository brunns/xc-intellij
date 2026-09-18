package ing.brunn.xcintellij.services

import com.intellij.execution.ProgramRunnerUtil
import com.intellij.execution.RunManager
import com.intellij.execution.configurations.ConfigurationTypeUtil
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import ing.brunn.xcintellij.run.XcConfigurationType
import ing.brunn.xcintellij.run.XcRunConfiguration

@Service(Service.Level.PROJECT)
class XcTaskRunnerService(
    private val project: Project,
) {
    @JvmOverloads
    fun runTask(
        taskName: String,
        parentDisposable: Disposable = project,
    ) {
        val runManager = RunManager.getInstance(project)
        val configType = ConfigurationTypeUtil.findConfigurationType(XcConfigurationType::class.java)
        val factory = configType.configurationFactories.first()

        val configName = "xc $taskName"

        // Locate existing configuration or create a new one
        val settings =
            runManager.findConfigurationByName(configName)
                ?: runManager.createConfiguration(configName, factory).also { newSettings ->
                    (newSettings.configuration as XcRunConfiguration).taskName = taskName
                    newSettings.isTemporary = false
                    runManager.addConfiguration(newSettings)
                }

        runManager.selectedConfiguration = settings

        try {
            ProgramRunnerUtil.executeConfiguration(
                settings,
                DefaultRunExecutor.getRunExecutorInstance(),
            )
        } catch (e: Exception) {
            thisLogger().warn("Failed to execute run configuration for task '$taskName'", e)
        }
    }
}
