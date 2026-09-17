package ing.brunn.xcintellij.run

import com.intellij.execution.Executor
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.LocatableRunConfigurationOptions
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.process.ProcessHandlerFactory
import com.intellij.execution.process.ProcessTerminatedListener
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import ing.brunn.xcintellij.services.XcSettingsState
import java.io.File

class XcRunConfigurationOptions : LocatableRunConfigurationOptions() {
    var taskName by string("")
}

class XcRunConfiguration(
    project: Project,
    factory: ConfigurationFactory,
    name: String,
) : LocatableConfigurationBase<XcRunConfigurationOptions>(project, factory, name) {
    override fun getOptions(): XcRunConfigurationOptions {
        return super.getOptions() as XcRunConfigurationOptions
    }

    var taskName: String
        get() = options.taskName ?: ""
        set(value) {
            options.taskName = value
        }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return XcRunConfigurationSettingsEditor()
    }

    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment,
    ): RunProfileState {
        return object : CommandLineState(environment) {
            override fun startProcess(): ProcessHandler {
                val basePath = project.basePath ?: ""
                val workDir = File(basePath)
                if (!workDir.exists()) {
                    workDir.mkdirs()
                }

                val xcExecutable = XcSettingsState.instance.xcExecutablePath

                val commandLine =
                    GeneralCommandLine(xcExecutable, taskName)
                        .withWorkDirectory(workDir)

                val processHandler =
                    ProcessHandlerFactory.getInstance()
                        .createColoredProcessHandler(commandLine)

                ProcessTerminatedListener.attach(processHandler)
                return processHandler
            }
        }
    }
}
