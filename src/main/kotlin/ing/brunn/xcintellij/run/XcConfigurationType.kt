package ing.brunn.xcintellij.run

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.icons.AllIcons
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NotNullLazyValue

class XcConfigurationType :
    ConfigurationTypeBase(
        ID,
        "xc Task",
        "Run xc tasks defined in Markdown",
        NotNullLazyValue.createValue { AllIcons.Actions.Execute },
    ) {
    init {
        addFactory(XcConfigurationFactory(this))
    }

    companion object {
        const val ID = "XcRunConfigurationType"
    }
}

class XcConfigurationFactory(
    type: XcConfigurationType,
) : ConfigurationFactory(type) {
    override fun getId(): String = "XcConfigurationFactory"

    override fun createTemplateConfiguration(project: Project): RunConfiguration = XcRunConfiguration(project, this, "xc")

    override fun getOptionsClass(): Class<out com.intellij.openapi.components.BaseState> = XcRunConfigurationOptions::class.java
}
