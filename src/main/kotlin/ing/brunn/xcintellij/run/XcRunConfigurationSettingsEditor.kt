package ing.brunn.xcintellij.run

import com.intellij.openapi.options.SettingsEditor
import com.intellij.ui.components.JBTextField
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class XcRunConfigurationSettingsEditor : SettingsEditor<XcRunConfiguration>() {
    internal val taskNameField = JBTextField()

    override fun createEditor(): JComponent {
        return panel {
            row("Task Name:") {
                cell(taskNameField)
                    .comment("Name of the xc task defined in your README.md")
            }
        }
    }

    override fun resetEditorFrom(configuration: XcRunConfiguration) {
        taskNameField.text = configuration.taskName
    }

    override fun applyEditorTo(configuration: XcRunConfiguration) {
        configuration.taskName = taskNameField.text
    }
}
